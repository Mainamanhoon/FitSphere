package com.example.fitsphere.screen.signup_activity

import androidx.lifecycle.viewModelScope
import com.example.common.Resource
import com.example.common.AppViewModel
import com.example.domain.repository.AccountService
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val accountService: AccountService
) : AppViewModel() {

    private val _signupFlow = MutableStateFlow<Resource<FirebaseUser>?>(null)
    val signupFlow: StateFlow<Resource<FirebaseUser>?> =_signupFlow

    val currentUser: FirebaseUser? get() = accountService.currentUser

    val name = MutableStateFlow("")
    val email = MutableStateFlow("")
    val password = MutableStateFlow("")
    val confirmPassword = MutableStateFlow("")




    fun updateEmail(newEmail:String){
        email.value = newEmail
    }
    fun updatePassword(newPassword:String){
        password.value = newPassword
    }
    fun updateConfirmPassword(newConfirmPassword:String){
        password.value = newConfirmPassword
    }

    fun signUp(){
        launchCatching {
            if(password.value !=confirmPassword.value){
                throw Exception("Passwords do not match")
            }
            viewModelScope.launch {
                _signupFlow.value = Resource.Loading
                val result = accountService.signUp(name.value,email.value,password.value)
                _signupFlow.value = result
            }
        }
    }
}