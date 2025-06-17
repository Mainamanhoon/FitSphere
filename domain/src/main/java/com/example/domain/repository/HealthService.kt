package com.example.domain.repository

import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.changes.Change
import androidx.health.connect.client.records.ExerciseSessionRecord
import androidx.health.connect.client.records.WeightRecord
import androidx.health.connect.client.units.Mass
import kotlinx.coroutines.flow.Flow
import java.time.Instant
import java.time.ZonedDateTime

interface HealthService {
    val healthConnectClient:HealthConnectClient?

    suspend fun writeWeightInput(weightInput:Double)
    suspend fun readWeightInputs(start:Instant, end:Instant):List<WeightRecord>
    suspend fun computeWeeklyAverage(start: Instant, end: Instant): Mass?
    suspend fun readExerciseSessions(start: Instant, end: Instant): List<ExerciseSessionRecord>
    suspend fun writeExerciseSession(start: ZonedDateTime, end: ZonedDateTime)
    suspend fun getChangesToken(): String
    suspend fun getChanges(token: String): Flow<ChangesMessage>
    fun isFeatureAvailable(feature:Int):Boolean
}

sealed class ChangesMessage {
    data class NoMoreChanges(val nextChangesToken: String) : ChangesMessage()
    data class ChangeList(val changes: List<Change>) : ChangesMessage()
}

