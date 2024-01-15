package com.example.gardeningjournal.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.gardeningjournal.database.PlantEntity
import com.example.gardeningjournal.database.PlantRepo
import kotlinx.coroutines.launch

class PlantDetailsViewModel(val plantRepo: PlantRepo): ViewModel() {
    private var _plantId: LiveData<Int>? = null

    val plantId: LiveData<Int>?
        get() = _plantId;
    fun getPlant(id: Int?): LiveData<PlantEntity> {
        return plantRepo.selectPlant(id)
    }
}

class PlantDetailsViewModelFactory(private val repository: PlantRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlantDetailsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PlantDetailsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}