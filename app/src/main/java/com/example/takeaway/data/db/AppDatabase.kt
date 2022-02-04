package com.example.takeaway.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.takeaway.data.model.RestaurantFeed

@Database(
    entities = [RestaurantFeed::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun RestaurantDao(): RestaurantDao
}
