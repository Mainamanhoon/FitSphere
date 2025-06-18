package com.example.data.mappers

 import com.example.data.room.VideoProgressEntity
import com.example.domain.model.VideoProgress


    fun VideoProgress.toEntity():VideoProgressEntity{
        return VideoProgressEntity(
                 tutorialId = this.tutorialId,
                 watchedTill = this.watchedTill,
                 totalDuration = this.totalDuration,
                 lastWatchedAt = this.lastWatchedAt,
                 isCompleted = this.isCompleted
             )
    }
    fun VideoProgressEntity.toDomain():VideoProgress{
        return  VideoProgress(
            tutorialId = this.tutorialId,
            watchedTill = this.watchedTill,
            totalDuration = this.totalDuration,
            lastWatchedAt = this.lastWatchedAt,
            isCompleted = this.isCompleted
        )

    }
