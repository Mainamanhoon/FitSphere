package com.example.fitsphere.screen.workout_activity

import androidx.lifecycle.viewModelScope
import com.example.common.Resource
import com.example.domain.model.Tutorial
import com.example.common.AppViewModel
import com.example.domain.repository.AccountService
import com.example.domain.repository.StorageService
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkoutActivityViewModel @Inject constructor(
    private val accountService: AccountService,
    private val storageService: StorageService
): AppViewModel() {
    private val  _dataState = MutableStateFlow<Resource<List<Tutorial>>?>(null)
    val dataState : StateFlow<Resource<List<Tutorial>>?> = _dataState


    val currentUser: FirebaseUser? get() = accountService.currentUser

    fun getTutorials(tutorials: List<String>){
        _dataState.value = Resource.Loading
        viewModelScope.launch {
            val result = storageService.fetchListOfDoc("tutorials", tutorials)
            when(result){
                is Resource.Success ->{
                    val result = result.result.mapNotNull { snapshot ->
                        snapshot.toObject(Tutorial::class.java)?.apply {
                            id = snapshot.id
                        }
                    }
                    _dataState.value = Resource.Success(result)
                }
                is Resource.Failure -> {
                    _dataState.value = Resource.Failure(result.exception)
                }
                else -> Unit
            }
        }
    }

}