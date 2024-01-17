package com.example.gardeningjournal.database

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PlantRepo(private val plantDao: PlantDao) {
    val plants: LiveData<Array<PlantEntity>> = plantDao.selectAllPlants()
    val plant: LiveData<PlantEntity> = plantDao.selectLatestPlant()
    suspend fun insertPlant(plant: PlantEntity) {
        withContext(Dispatchers.IO) {
            plantDao.insertPlant(plant)
        }
    }

    fun selectPlant(id: Int?): LiveData<PlantEntity> {
        if(id == null) return plantDao.selectLatestPlant()
        return plantDao.selectPlant(id);
    }

    suspend fun selectPlantById(id: Int): PlantEntity {
        return plantDao.selectPlantById(id);
    }
}
