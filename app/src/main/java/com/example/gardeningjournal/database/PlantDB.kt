package com.example.gardeningjournal.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [PlantEntity::class], version = 1)
abstract class PlantDB: RoomDatabase() {
    abstract fun plantDao(): PlantDao
}