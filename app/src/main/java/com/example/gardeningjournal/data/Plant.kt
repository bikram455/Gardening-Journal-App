package com.example.foodapp.data

import java.io.Serializable
import java.util.Date

data class Plant(
    var name: String,
    var type: String,
    var wateringFrequency: String,
    var plantedDate: String
) {
    constructor(name: String, instruction: String, wateringFrequency: String):
            this(name, instruction, wateringFrequency,  Date().toString()){
        this.name = name;
        this.type = type;
        this.wateringFrequency = wateringFrequency;
    }
}