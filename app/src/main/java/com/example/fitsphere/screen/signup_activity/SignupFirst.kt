package com.example.fitsphere.screen.signup_activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
 import androidx.navigation.fragment.findNavController
import com.example.fitsphere.Loader
import com.example.fitsphere.R
import com.example.fitsphere.databinding.FragmentSignupFirstBinding


class SignupFirst : androidx.fragment.app.Fragment() {
     var _binding : FragmentSignupFirstBinding? = null
     val binding get() = _binding!!

    val signupViewModel : SignupViewModel by activityViewModels()
    lateinit var loader : Loader

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignupFirstBinding.inflate(layoutInflater)
        loader = Loader(requireContext())

        binding.btnNext.setOnClickListener{
            val name = binding.etFullName.text
            val phnNumber = binding.etMobileNumber.text
            val email = binding.etEmail.text

            if(name.isEmpty() ||email.isEmpty() || phnNumber.isEmpty()){
                Toast.makeText(context, "Fields Cannot be left blank", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            saveDetails()
            findNavController().navigate(R.id.action_signUpFragment_to_signupSecond)
        }
        return binding.root


    }

    fun saveDetails(){

        signupViewModel.name.value = binding.etFullName.text.toString()
        signupViewModel.email.value = binding.etEmail.text.toString()
    }

}