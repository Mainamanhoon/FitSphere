package com.example.fitsphere.data


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.Resource
import com.example.domain.repository.AccountService
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val accountService: AccountService
) :ViewModel(){

    private val _loginFlow = MutableStateFlow<Resource<FirebaseUser>?>(null)
    val loginFlow: StateFlow<Resource<FirebaseUser>?> =_loginFlow

    private val _signupFlow = MutableStateFlow<Resource<FirebaseUser>?>(null)
    val signupFlow: StateFlow<Resource<FirebaseUser>?> =_signupFlow

    val currentUser:FirebaseUser? get() = accountService.currentUser

    init{
        if(currentUser!=null){
            _loginFlow.value = Resource.Success(accountService.currentUser!!)
        }
    }

    fun login(email:String, password:String){
        viewModelScope.launch{
            _loginFlow.value = Resource.Loading
            val result = accountService.login(email,password)
            _loginFlow.value = result
        }
    }
    fun signUp(name:String,email:String, password:String){
        viewModelScope.launch {
            _signupFlow.value = Resource.Loading
            val result = accountService.signUp(name,email,password)
            _signupFlow.value = result
        }

    }
    fun logOut(){
        accountService.signOut()
        _loginFlow.value = null
        _signupFlow.value = null
    }

}