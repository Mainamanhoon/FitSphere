package com.example.domain.model

data class VideoProgress(
    val tutorialId: String,
    val watchedTill: Long, // timestamp in seconds
    val totalDuration: Long, // total duration in seconds
    val lastWatchedAt: Long = System.currentTimeMillis(), // when user last watched
    val isCompleted: Boolean = false // true if watched > 90% of video
)