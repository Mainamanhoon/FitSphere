package com.example.fitsphere.screen.signup_activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.fitsphere.Loader
import com.example.fitsphere.data.service.Resource
import com.example.fitsphere.model.User
import com.example.fitsphere.screen.login_activity.LoginActivity
import com.example.fitsphere.databinding.FragmentSignupSecondBinding
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import kotlinx.coroutines.launch


class SignupSecond : Fragment() {

   var _binding : FragmentSignupSecondBinding? = null
   val binding get() = _binding!!
    val signupViewModel : SignupViewModel by activityViewModels()
    lateinit var loader: Loader

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignupSecondBinding.inflate(layoutInflater)
        loader = Loader(requireContext())

        viewLifecycleOwner.lifecycleScope.launch {
            signupViewModel.signupFlow.collect { resource ->
                when (resource) {
                    is Resource.Failure -> {
                        loader.cancel()
                        if (resource.exception is FirebaseAuthUserCollisionException) {
                            Toast.makeText(context, "User already exists. Please log in.", Toast.LENGTH_LONG).show()
                            navigateToLogin()
                        }
                        else if(resource.exception is FirebaseAuthWeakPasswordException){
                            Toast.makeText(context, "Use Strong 6 digit Password", Toast.LENGTH_LONG).show()
                        }
                        else {
                            Toast.makeText(context, "Signup Failed", Toast.LENGTH_LONG).show()
                        }
                    }
                    is Resource.Loading -> {
                        loader.show()
                    }
                    is Resource.Success -> {
                        loader.cancel()
                        Toast.makeText(context, "Signup Successful!", Toast.LENGTH_LONG).show()
                        resource.result.let {
                             User(uid = it.uid,
                                 name = it.displayName.orEmpty(),
                                  email = it.email.orEmpty(),
                                 phnNumber = it.phoneNumber.toString().orEmpty(),
                                 photoUri = it.photoUrl.toString().orEmpty(),
                                 isAnonymous = it.isAnonymous,
                             )
                        }
                        navigateToLogin()
                    }
                    else -> {
                        // Do nothing if the resource is null or idle
                    }
                }
            }
        }


        binding.btnSignup.setOnClickListener{
            val confirmPassword = binding.etConfirmPassword.text.toString()
            val password = binding.etCreatePassword.text.toString()
            Log.d("Hello", "password : $password ConfirmPassword : $confirmPassword")
            if(password.isEmpty() || confirmPassword.isEmpty()){
                Toast.makeText(context, "Fields can't be left blank", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(confirmPassword!=password){
                Toast.makeText(context, "Password do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            saveDetails()
            signupViewModel.signUp()
        }

        return binding.root
    }


    private fun navigateToLogin() {
        val intent = Intent(requireContext(), LoginActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    fun saveDetails(){
        signupViewModel.password.value = binding.etCreatePassword.text.toString()
        signupViewModel.confirmPassword.value = binding.etConfirmPassword.text.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
         _binding = null
    }


}