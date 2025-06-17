package com.example.fitsphere.screen.main_activity

import androidx.activity.result.ActivityResultLauncher
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.HeartRateRecord
import androidx.health.connect.client.records.StepsRecord
import androidx.health.connect.client.records.TotalCaloriesBurnedRecord
import androidx.health.connect.client.records.WeightRecord
import androidx.lifecycle.viewModelScope
import com.example.common.Resource
import com.example.domain.model.Workout
import com.example.common.AppViewModel
import com.example.domain.repository.AccountService
import com.example.domain.repository.HealthService
import com.example.domain.repository.StorageService
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val accountService: AccountService,
    private val storageService: StorageService,
    private val healthService: HealthService
): AppViewModel(){
    private val  _dataState = MutableStateFlow<Resource<List<Workout>>?>(null)
    val dataState : StateFlow<Resource<List<Workout>>?> = _dataState
//    private lateinit var requestPermissions: ActivityResultLauncher<Set<String>>


    val currentUser:FirebaseUser? get() = accountService.currentUser
    val healthConnectClient:HealthConnectClient? get() = healthService.healthConnectClient








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