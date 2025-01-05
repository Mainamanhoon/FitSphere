package com.example.fitsphere.screen.login_activity

import androidx.lifecycle.viewModelScope
import com.example.fitsphere.AppViewModel
import com.example.fitsphere.data.service.AccountService
import com.example.fitsphere.data.service.Resource
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val accountService: AccountService
) : AppViewModel() {

    private val _loginFlow = MutableStateFlow<Resource<FirebaseUser>?>(null)
    val loginFlow : StateFlow<Resource<FirebaseUser>?> = _loginFlow
    val currentUser:FirebaseUser? get() = accountService.currentUser

    init{
        if(currentUser!=null){
            _loginFlow.value = Resource.Success(accountService.currentUser!!)
        }
    }

    var email:String = ""
    var password:String =""

    fun signIn(email: String, password:String){
        viewModelScope.launch {
            _loginFlow.value = Resource.Loading
            val result = accountService.login(email,password)
            _loginFlow.value = result
        }
    }
}






