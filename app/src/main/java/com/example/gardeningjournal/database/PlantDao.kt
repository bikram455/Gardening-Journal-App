package com.example.gardeningjournal.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PlantDao {
    @Insert
    suspend fun insertPlant(plant: PlantEntity)

    @Query("Select * from plants")
    fun selectAllPlants(): LiveData<Array<PlantEntity>>

    @Query("Select * from plants order by addedDate DESC limit 1")
    fun selectLatestPlant(): LiveData<PlantEntity>
    @Query("Select * from plants where id= :id")
    fun selectPlant(id: Int?): LiveData<PlantEntity>

    @Query("Select * from plants where id= :id")
    fun selectPlantById(id: Int?): PlantEntity
}