package com.example.takeaway.data.di

import android.app.Application
import com.example.takeaway.data.db.AppDatabase
import com.example.takeaway.data.db.RestaurantFeedDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun provideDb(app: Application) = AppDatabase.getDbInstance(app)

    @Provides
    fun provideRestaurantFeedDao(db: AppDatabase): RestaurantFeedDao {
        return db.RestaurantsDao()
    }
}