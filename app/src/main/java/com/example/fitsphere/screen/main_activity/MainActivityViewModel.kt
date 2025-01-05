package com.example.fitsphere.screen.main_activity

import androidx.lifecycle.viewModelScope
import com.example.fitsphere.AppViewModel
import com.example.fitsphere.data.service.AccountService
import com.example.fitsphere.data.service.Resource
import com.example.fitsphere.data.service.StorageService
import com.example.fitsphere.model.Workout
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val accountService: AccountService,
    private val storageService: StorageService
): AppViewModel(){
    private val  _dataState = MutableStateFlow<Resource<List<Workout>>?>(null)
    val dataState : StateFlow<Resource<List<Workout>>?> = _dataState


    val currentUser:FirebaseUser? get() = accountService.currentUser

    fun getWorkouts(){
        _dataState.value = Resource.Loading
        viewModelScope.launch {
            val result = storageService.fetchCollection("workouts")
            when(result){
                is Resource.Success ->{
                    val workouts = result.result.mapNotNull { snapshot ->
                        snapshot.toObject(Workout::class.java)?.apply {
                            id = snapshot.id
                        }
                    }
                    _dataState.value = Resource.Success(workouts)
                }
                is Resource.Failure -> {
                    _dataState.value = Resource.Failure(result.exception)
                }
                else -> Unit
            }
        }
    }

}