package com.example.fitsphere.model

data class User(
    val uid: String = "",
    val name:String = "",
    val email: String = "",
    val phnNumber: String = "",
    val photoUri:String ="",
    val isAnonymous: Boolean = true,
)