package com.example.whatnow

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.whatnow.databinding.ActivityHomeBinding
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

class HomeActivity : AppCompatActivity() {

    private var mInterstitialAd: InterstitialAd? = null
    private var category = ""

    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityHomeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        MobileAds.initialize(this) {}

        loadAd()

        binding.tecNews.setOnClickListener {
            showAd()
            category = "technology"
             }

        binding.generalNews.setOnClickListener {
            showAd()
            category = "general"
             }

        binding.scineceNews.setOnClickListener {
            showAd()
            category = "science"
             }

        binding.spotNews.setOnClickListener {
            showAd()
            category = "sport"
             }

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

    private fun openNewsPage() {
        val i = Intent(this, MainActivity::class.java)
        i.putExtra("category",category)
        startActivity(i)
    }
    private fun loadAd(){
        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(this,"ca-app-pub-3940256099942544/1033173712", adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                mInterstitialAd = interstitialAd
            }
        })
    }
    private fun showAd(){
        if (mInterstitialAd != null){
            mInterstitialAd?.show(this)

            mInterstitialAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    mInterstitialAd = null
                    openNewsPage()
                }

                override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                    mInterstitialAd = null
                    openNewsPage()
                }
            }
        }
        else
            openNewsPage()
    }
}