package com.example.takeaway.di

import android.app.Application
import androidx.room.Room
import com.example.takeaway.data.db.AppDatabase
import com.example.takeaway.data.db.RestaurantDao
import com.example.takeaway.utils.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    @Singleton
    fun provideDb(
        application: Application,
    ): AppDatabase {
        return Room
            .databaseBuilder(application, AppDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideRestaurantFeedDao(db: AppDatabase): RestaurantDao {
        return db.RestaurantDao()
    }
}

