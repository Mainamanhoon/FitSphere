package com.example.fitsphere.screen.signup_activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.fitsphere.data.AuthViewModel
import com.example.fitsphere.databinding.ActivitySignupBinding
import com.example.fitsphere.screen.login_activity.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupActivity : AppCompatActivity() {
    private var _binding : ActivitySignupBinding? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySignupBinding.inflate(layoutInflater)

         val signupViewModel by viewModels<AuthViewModel>()

        binding.tvLogin.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        setContentView(binding.root)
    }

}