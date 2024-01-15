package com.example.gardeningjournal.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "plants")
class PlantEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id: Int?,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "type") var type: String,
    @ColumnInfo(name = "wateringFrequency") var wateringFrequency: Int,
    @ColumnInfo(name = "plantedDate") var plantedDate: String,
    @ColumnInfo(name = "addedDate") var addedDate: Long
){
}