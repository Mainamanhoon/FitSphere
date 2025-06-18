package com.example.domain.use_cases

import android.util.Log
import com.example.domain.model.VideoProgress
import com.example.domain.repository.youtubePlayerRepository

class StreamVideoUseCase {
    fun extractVideoId(input: String): String? {
        if (input.isNullOrBlank()) return null

        val cleanInput = input.trim()
        Log.d("VideoPlayer", "Extracting from: '$cleanInput'")

        // If it's already just an ID (11 characters)
        if (cleanInput.matches(Regex("^[a-zA-Z0-9_-]{11}$"))) {
            Log.d("VideoPlayer", "Already a video ID: $cleanInput")
            return cleanInput
        }

        // Extract from various URL formats
        val patterns = listOf(
            "(?:youtube\\.com/watch\\?v=)([a-zA-Z0-9_-]{11})",
            "(?:youtu\\.be/)([a-zA-Z0-9_-]{11})",
            "(?:youtube\\.com/embed/)([a-zA-Z0-9_-]{11})",
            "(?:youtube\\.com/v/)([a-zA-Z0-9_-]{11})"
        )

        patterns.forEach { pattern ->
            val regex = Regex(pattern)
            val matchResult = regex.find(cleanInput)
            matchResult?.groupValues?.get(1)?.let {
                Log.d("VideoPlayer", "Extracted ID: $it")
                return it
            }
        }

        Log.e("VideoPlayer", "Failed to extract video ID from: $cleanInput")
        return null
    }
}

 class SaveVideoProgressUseCase(
    private val repository: youtubePlayerRepository
) {
    suspend operator fun invoke(
        tutorialId: String,
        currentPosition: Long,
        totalDuration: Long
    ): Result<Unit> {
        val isCompleted = (currentPosition.toFloat() / totalDuration.toFloat()) > 0.9f

        val progress = VideoProgress(
            tutorialId = tutorialId,
            watchedTill = currentPosition,
            totalDuration = totalDuration,
            lastWatchedAt = System.currentTimeMillis(),
            isCompleted = isCompleted
        )

        return repository.saveProgress(progress)
    }
}

 class GetVideoProgressUseCase(
    private val repository: youtubePlayerRepository
) {
    suspend operator fun invoke(tutorialId: String): Result<VideoProgress?> {
        return repository.getProgress(tutorialId)
    }
}

 class ClearVideoProgressUseCase(
    private val repository: youtubePlayerRepository
) {
    suspend operator fun invoke(tutorialId: String): Result<Unit> {
        return repository.deleteProgress(tutorialId)
    }
}