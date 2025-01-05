package com.example.fitsphere.data.impl

import com.example.fitsphere.data.service.AccountService
import com.example.fitsphere.data.service.Resource
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.auth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class AccountServiceImpl @Inject constructor (
    private val firebaseAuth: FirebaseAuth
): AccountService {

    override val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    override val currentUserId: String
        get() = firebaseAuth.currentUser?.uid.orEmpty()

    override fun hasUser(): Boolean {
        return Firebase.auth.currentUser !=null
    }

    override suspend fun login(email: String, password: String): Resource<FirebaseUser> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email,password).await()
            Resource.Success(result.user!!)
        }catch (e:Exception){
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun signUp(
        name: String,
        email: String,
        password: String
    ): Resource<FirebaseUser> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email,password).await()
            result?.user?.updateProfile(UserProfileChangeRequest.Builder().setDisplayName(name).build())
                ?.await()
            Resource.Success(result.user!!)
        }catch (e:Exception){
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override  fun signOut() {
        firebaseAuth.signOut()
    }
}