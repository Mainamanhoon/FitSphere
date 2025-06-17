package com.example.data.healthconnect

//import android.content.Context
//import android.os.Build
//import android.widget.Toast
//import androidx.activity.result.contract.ActivityResultContract
//import androidx.health.connect.client.HealthConnectClient
//import androidx.health.connect.client.HealthConnectClient.Companion.SDK_AVAILABLE
//import androidx.health.connect.client.HealthConnectFeatures
//import androidx.health.connect.client.PermissionController
//import androidx.health.connect.client.changes.Change
//import androidx.health.connect.client.records.ExerciseSessionRecord
//import androidx.health.connect.client.records.HeartRateRecord
//import androidx.health.connect.client.records.Record
//import androidx.health.connect.client.records.StepsRecord
//import androidx.health.connect.client.records.TotalCaloriesBurnedRecord
//import androidx.health.connect.client.records.WeightRecord
//import androidx.health.connect.client.records.metadata.DataOrigin
//import androidx.health.connect.client.records.metadata.Metadata
//import androidx.health.connect.client.request.AggregateRequest
//import androidx.health.connect.client.request.ChangesTokenRequest
//import androidx.health.connect.client.request.ReadRecordsRequest
//import androidx.health.connect.client.time.TimeRangeFilter
//import androidx.health.connect.client.units.Energy
//import androidx.health.connect.client.units.Mass
//import androidx.work.OneTimeWorkRequestBuilder
//import androidx.work.WorkManager
//import com.example.data.model.ExerciseSessionData
//import com.example.data.worker.ReadStepWorker
//import com.example.domain.repository.ChangesMessage
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.flow
//import java.io.IOException
//import java.time.Instant
//import java.time.ZonedDateTime
//import java.util.concurrent.TimeUnit
//import kotlin.random.Random
//
//// The minimum android level that can use Health Connect
//
//
//class HealthConnectManager(private val context: Context) {
//    private val healthConnectClient by lazy { HealthConnectClient.getOrCreate(context) }
//
//
//
//
//
//
//
//
//
//    suspend fun hasAllPermissions(permissions: Set<String>): Boolean {
//        return healthConnectClient.permissionController.getGrantedPermissions().containsAll(permissions)
//    }
//
//    fun requestPermissionsActivityContract(): ActivityResultContract<Set<String>, Set<String>> {
//        return PermissionController.createRequestPermissionResultContract()
//    }
//
//
//
//
//
//
//
//
//}
//
//
