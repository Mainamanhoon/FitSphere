package com.example.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [VideoProgressEntity::class],
    version =1,
    exportSchema = false
)

abstract class VideoProgressDatabase :RoomDatabase() {

    abstract fun getVideoProgressDao(): VideoProgressDao
}