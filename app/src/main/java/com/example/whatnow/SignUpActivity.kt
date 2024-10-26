package com.example.whatnow

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.whatnow.databinding.ActivitySignUpBinding
import com.google.android.gms.ads.MobileAds
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        MobileAds.initialize(this) {}
        auth = Firebase.auth


        binding.signupBtn.setOnClickListener {
            val email = binding.emailEt.text.toString()
            val password = binding.passEt.text.toString()
            val confirmPassword = binding.conPassEt.text.toString()
            if (email.isBlank() || password.isBlank() || confirmPassword.isBlank())
                Toast.makeText(this, "Missing Required Fields", Toast.LENGTH_SHORT).show()
            else if (password.length < 6)
                Toast.makeText(this, "Password is too short", Toast.LENGTH_SHORT).show()
            else if (password != confirmPassword)
                Toast.makeText(this, "Passwords don't match", Toast.LENGTH_SHORT).show()
            else {
                addUser(email, password)
            }

            binding.haveAccountTv.setOnClickListener {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }

    }

    private fun verifyEmail() {
        val user = Firebase.auth.currentUser

        user!!.sendEmailVerification()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Check your email", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun addUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful)
                    verifyEmail()
                 else
                    Toast.makeText(this, task.exception?.localizedMessage, Toast.LENGTH_SHORT).show()
            }
    }


    public override fun onStart() {
            super.onStart()
            val currentUser = auth.currentUser
            if (currentUser != null && currentUser.isEmailVerified) {
                startActivity(Intent(this, HomeActivity::class.java))
                finish()

            }
        }
    }


