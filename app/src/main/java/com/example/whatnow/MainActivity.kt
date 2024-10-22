package com.example.whatnow

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.example.whatnow.databinding.ActivityMainBinding
import com.google.android.gms.ads.AdRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    //https://newsapi.org/v2/top-headlines?country=us&category=general&apiKey=3be5b491ae3d4dca8522454d878c8b89&pageSize=30

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getNews()
        binding.swipeRefresh.setOnRefreshListener {
            getNews()
        }

        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)


    }

    private fun getNews() {

        val cat = intent.getStringExtra("category")!!
        val code = getSharedPreferences("country", MODE_PRIVATE)
            .getString("code","eg")!!

        val retrofit = Retrofit
            .Builder()
            .baseUrl("https://newsapi.org")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val c = retrofit.create(NewsCallable::class.java)
        c.getNews(cat, code).enqueue(object : Callback<News> {
            override fun onResponse(p0: Call<News>, p1: Response<News>) {
                val articles = p1.body()?.articles!!
                //Log.d("trace", "Data: ${articles}")
                val adapter = NewsAdapter(this@MainActivity, articles)
                binding.newsList.adapter = adapter
                binding.progress.isVisible = false
                binding.swipeRefresh.isRefreshing = false


            }

            override fun onFailure(p0: Call<News>, p1: Throwable) {
                Log.d("trace", "Error: ${p1.localizedMessage}")
                binding.progress.isVisible = false
                binding.swipeRefresh.isRefreshing = false
            }
        })

    }

}