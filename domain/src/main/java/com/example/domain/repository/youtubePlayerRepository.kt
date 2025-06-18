package com.example.domain.repository

import com.example.common.Resource
import com.example.domain.model.Tutorial
import com.example.domain.model.VideoProgress
import com.example.domain.model.WatchHistory

interface youtubePlayerRepository {
    suspend fun saveProgress(progress: VideoProgress): Result<Unit>
    suspend fun getProgress(tutorialId: String): Result<VideoProgress?>
    suspend fun getAllProgress(): Result<List<VideoProgress>>
    suspend fun deleteProgress(tutorialId: String): Result<Unit>
    suspend fun getWatchHistory(): Result<List<WatchHistory>>
}
