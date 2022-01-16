package com.example.takeaway.data.db
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.takeaway.data.model.RestaurantFeed

@Database(
    entities = [RestaurantFeed::class],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun RestaurantsDao(): RestaurantFeedDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getDbInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "takeaway-db")
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}
