package com.example.gardeningjournal.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [PlantEntity::class], version = 1)
abstract class PlantDB: RoomDatabase() {
    abstract fun plantDao(): PlantDao
    companion object {
        @Volatile private var instance: PlantDB? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }
        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            PlantDB::class.java,
            "plant_database"
        ).build()
    }
}