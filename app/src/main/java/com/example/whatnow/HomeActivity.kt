package com.example.whatnow

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.whatnow.databinding.ActivityHomeBinding
import com.google.android.gms.ads.MobileAds

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityHomeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        MobileAds.initialize(this) {}

        binding.tecNews.setOnClickListener { openNewsPage("technology") }

        binding.generalNews.setOnClickListener { openNewsPage("general") }

        binding.scineceNews.setOnClickListener { openNewsPage("science") }

        binding.spotNews.setOnClickListener { openNewsPage("sport") }

        binding.topAppBar.setOnMenuItemClickListener {
            return@setOnMenuItemClickListener when(it.itemId){
                R.id.countries_item -> {
                    startActivity(Intent(this, CountryActivity::class.java))
                    true
                }
                else -> false
            }

        }


    }

    private fun openNewsPage(categroy: String) {
        val i = Intent(this, MainActivity::class.java)
        i.putExtra("category", categroy)
        startActivity(i)
    }
}