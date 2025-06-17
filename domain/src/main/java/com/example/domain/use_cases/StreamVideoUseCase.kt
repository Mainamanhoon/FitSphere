package com.example.domain.use_cases

//import com.example.domain.model.VideoProgress
//import com.example.domain.repository.youtubePlayerRepository
//
// class SaveVideoProgressUseCase(
//    private val repository: youtubePlayerRepository
//) {
//    suspend operator fun invoke(
//        tutorialId: String,
//        currentPosition: Long,
//        totalDuration: Long
//    ): Result<Unit> {
//        val isCompleted = (currentPosition.toFloat() / totalDuration.toFloat()) > 0.9f
//
//        val progress = VideoProgress(
//            tutorialId = tutorialId,
//            watchedTill = currentPosition,
//            totalDuration = totalDuration,
//            lastWatchedAt = System.currentTimeMillis(),
//            isCompleted = isCompleted
//        )
//
//        return repository.saveProgress(progress)
//    }
//}
//
// class GetVideoProgressUseCase(
//    private val repository: youtubePlayerRepository
//) {
//    suspend operator fun invoke(tutorialId: String): Result<VideoProgress?> {
//        return repository.getProgress(tutorialId)
//    }
//}
//
// class ClearVideoProgressUseCase(
//    private val repository: youtubePlayerRepository
//) {
//    suspend operator fun invoke(tutorialId: String): Result<Unit> {
//        return repository.deleteProgress(tutorialId)
//    }
//}