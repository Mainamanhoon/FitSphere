package com.example.domain.repository

import com.example.common.Resource
import com.google.firebase.auth.FirebaseUser

interface AccountService {
    val currentUser : FirebaseUser?
    val currentUserId:String
    fun hasUser():Boolean

    suspend fun login(email:String, password:String): Resource<FirebaseUser>
    suspend fun signUp(name:String, email: String,password: String): Resource<FirebaseUser>
    fun signOut()


}
