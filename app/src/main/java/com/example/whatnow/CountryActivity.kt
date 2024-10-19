package com.example.whatnow

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.whatnow.databinding.ActivityCountryBinding


class CountryActivity : AppCompatActivity() {
    private lateinit var binding : ActivityCountryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCountryBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.group.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId) {

                R.id.rb_eg -> changeCountry("eg")
                R.id.rb_us -> changeCountry("us")
                R.id.rb_de -> changeCountry("de")

            }
        }








    }
    private fun changeCountry(code : String){
        val prefs = getSharedPreferences("country", MODE_PRIVATE).edit()
        prefs.putString("code", code)
        prefs.apply()
        Toast.makeText(this, "Changed!", Toast.LENGTH_SHORT).show()
    }
}