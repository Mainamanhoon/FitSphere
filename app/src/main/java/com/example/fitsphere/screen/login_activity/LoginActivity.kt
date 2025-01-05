package com.example.fitsphere.screen.login_activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import com.example.fitsphere.Loader
import com.example.fitsphere.data.service.Resource
import com.example.fitsphere.databinding.ActivityLoginBinding
import com.example.fitsphere.screen.main_activity.MainActivity
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    var _binding : ActivityLoginBinding?= null
    val binding get() = _binding!!
    var email:String =""
    var password :String = ""
    lateinit var  loader: Loader
    val loginViewModel by  viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
         setContentView(binding.root)

        loader = Loader(this)

        binding.etEmail.doOnTextChanged{text, start, before, count ->
             email = text.toString()
        }
        binding.etPassword.doOnTextChanged{text, start, before, count ->
            password = text.toString()
        }


        lifecycleScope.launch {
            loginViewModel.loginFlow.collect{ resource ->
                when(resource){
                    is Resource.Failure -> {
                        if(resource.exception is FirebaseAuthInvalidCredentialsException){
                            Toast.makeText(this@LoginActivity, "Please enter correct password", Toast.LENGTH_LONG).show()
                        }
                        if(resource.exception is FirebaseAuthInvalidUserException){
                            Toast.makeText(this@LoginActivity,"No account found with this Email",Toast.LENGTH_LONG).show()
                        }
                        else Toast.makeText(this@LoginActivity,"Error signing you up",Toast.LENGTH_SHORT).show()
                        loader.cancel()
                    }
                    is Resource.Loading -> {
                        loader.show()
                    }
                    is Resource.Success-> {
                        Toast.makeText(this@LoginActivity,"Logged in Successfully",Toast.LENGTH_LONG).show()
                         loader.cancel()
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    else ->Unit
                }

            }
        }

        binding.signinBtn.setOnClickListener{
            if(email.isEmpty()||password.isEmpty()){
                Toast.makeText(this@LoginActivity,"Enter valid email and password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            loginViewModel.signIn(email,password)
        }
    }
}