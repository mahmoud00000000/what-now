package com.example.whatnow

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.whatnow.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        binding.loginBtn.setOnClickListener {
            val email = binding.emailEt.text.toString()
            val password = binding.passEt.text.toString()
            if (email.isBlank() || password.isBlank())
                Toast.makeText(this, "Missing Required Fields", Toast.LENGTH_SHORT).show()
            else if (password.length < 6)
                Toast.makeText(this, "Password is too short", Toast.LENGTH_SHORT).show()
            else {
                loginUser(email, password)
            }

            binding.noAccountTv.setOnClickListener {
                startActivity(Intent(this, SignUpActivity::class.java))
                finish()
            }
            binding.forgetPassTv.setOnClickListener {
                val email = binding.emailEt.text.toString()
                if (email.isEmpty())
                    Toast.makeText(this, "Write your email first", Toast.LENGTH_SHORT).show()
                else
                    Firebase.auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(this, "Check your email", Toast.LENGTH_SHORT).show()
                            }
                        }
            }

        }
    }

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    if (auth.currentUser!!.isEmailVerified) {
                        startActivity(Intent(this, HomeActivity::class.java))
                        finish()
                    } else
                        Toast.makeText(this, "Check your email", Toast.LENGTH_SHORT).show()

                }
                else
                    Toast.makeText(this, task.exception?.localizedMessage, Toast.LENGTH_SHORT).show()

            }
    }
}


