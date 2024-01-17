package com.example.gardeningjournal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.gardeningjournal.data.GardenLogViewModel
import com.example.gardeningjournal.data.GardenLogViewModelFactory
import com.example.gardeningjournal.data.PlantDetailsViewModel
import com.example.gardeningjournal.data.PlantDetailsViewModelFactory
import com.example.gardeningjournal.database.PlantDB
import com.example.gardeningjournal.database.PlantEntity
import com.example.gardeningjournal.database.PlantRepo
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import kotlin.coroutines.CoroutineContext

class PlantDetailsFragment : Fragment(), CoroutineScope {
    private lateinit var plantName: TextView
    private lateinit var plantType: TextView
    private lateinit var wateringFrequency: TextView
    private lateinit var plantedDate: TextView
    private lateinit var backButton: Button

    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_plant_details, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PlantDetailsFragment().apply {

            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        plantName = view.findViewById(R.id.plantName)
        plantType = view.findViewById(R.id.plantType)
        plantedDate = view.findViewById(R.id.plantedDate)
        wateringFrequency = view.findViewById(R.id.wateringFrequency)
        backButton = view.findViewById(R.id.back)

        val application = requireNotNull(this.activity).application

        val plantId = arguments?.getInt("position", -1)

        job = Job()
        launch {
            context?.let {
                val plant  = PlantDB(it).plantDao().selectPlantById(plantId)
                println("Plant fetched ${plant.name}")
                plantName.text = "Plant Name: ${plant.name}"
                plantType.text = "Plant Type: ${plant.type}"
                wateringFrequency.text = "Needs to be watered every ${plant.wateringFrequency.toString()} days."
                plantedDate.text = "Date planted: ${plant.plantedDate}"
            }
        }

        backButton.setOnClickListener{
            findNavController().popBackStack()
        }
    }
}