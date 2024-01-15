package com.example.gardeningjournal.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.gardeningjournal.database.PlantEntity
import com.example.gardeningjournal.database.PlantRepo
import kotlinx.coroutines.launch

class GardenLogViewModel(private val plantRepo: PlantRepo): ViewModel() {
    private var _plants: LiveData<Array<PlantEntity>> = plantRepo.plants
    val plants: LiveData<Array<PlantEntity>>
        get() = _plants;

    fun insertPlant(plant: PlantEntity) {
        viewModelScope.launch {
            plantRepo.insertPlant(plant)
        }
    }
}

class GardenLogViewModelFactory(private val repository: PlantRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GardenLogViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GardenLogViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}