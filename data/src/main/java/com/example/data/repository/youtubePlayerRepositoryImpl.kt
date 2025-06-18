package com.example.data.repository

import com.example.common.Resource
import com.example.data.mappers.toDomain
import com.example.data.mappers.toEntity
import com.example.data.room.VideoProgressDao
import com.example.domain.model.Tutorial
import com.example.domain.model.VideoProgress
import com.example.domain.model.WatchHistory
import com.example.domain.repository.youtubePlayerRepository
import javax.inject.Inject

class youtubePlayerRepositoryImpl @Inject constructor(
    private val videoProgressDao: VideoProgressDao
) : youtubePlayerRepository {
    override suspend fun saveProgress(progress: VideoProgress): Result<Unit> {
        return try {
            videoProgressDao.insertProgress(progress.toEntity())
            Result.success(Unit)
        }catch (e:Exception){
            Result.failure(e)
        }
    }

    override suspend fun getProgress(tutorialId: String): Result<VideoProgress?> {
        return try {
            val entity = videoProgressDao.getProgress(tutorialId)
            Result.success(entity?.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getAllProgress(): Result<List<VideoProgress>> {
        return try {
            val entities = videoProgressDao.getAllProgress()
            Result.success(entities.map { it.toDomain() })
        } catch (e: Exception) {
            Result.failure(e)
        }    }

    override suspend fun deleteProgress(tutorialId: String): Result<Unit> {
        return try {
            videoProgressDao.deleteProgress(tutorialId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getWatchHistory(): Result<List<WatchHistory>> {
        return try {
            val progressList = videoProgressDao.getAllProgress()
            val watchHistoryList = progressList.map { progressEntity ->
//                val tutorial = tutorialRepository.getTutorialById(progressEntity.tutorialId)
                val tutorial = Tutorial(id = progressEntity.tutorialId )
                WatchHistory(
                    tutorial = tutorial,
                    progress = progressEntity.toDomain()
                )
            }
            Result.success(watchHistoryList)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}