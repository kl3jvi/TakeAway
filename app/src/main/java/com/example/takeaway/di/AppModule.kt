package com.example.takeaway.di

import com.example.takeaway.data.db.RestaurantDao
import com.example.takeaway.data.restaurant.RestaurantRepository
import com.example.takeaway.data.restaurant.RestaurantRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    fun providesRestaurantFeedRepository(dao: RestaurantDao, ioDispatcher: CoroutineDispatcher):
            RestaurantRepository = RestaurantRepositoryImpl(dao, ioDispatcher)
}