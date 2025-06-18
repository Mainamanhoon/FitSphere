package com.example.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.domain.model.Tutorial
import com.example.domain.model.VideoProgress

@Dao
interface VideoProgressDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProgress(progress: VideoProgressEntity)

    @Query("SELECT * FROM video_progress WHERE tutorialId = :tutorialId")
    suspend fun getProgress(tutorialId: String):VideoProgressEntity?

    @Query("SElECT * FROM video_progress ORDER BY lastWatchedAt DESC")
    suspend fun getAllProgress(): List<VideoProgressEntity>

    @Query("DELETE FROM video_progress WHERE tutorialId = :tutorialId")
    suspend fun deleteProgress(tutorialId: String)



}