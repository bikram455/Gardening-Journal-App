package com.example.gardeningjournal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.gardeningjournal.data.GardenLogViewModel
import com.example.gardeningjournal.data.GardenLogViewModelFactory
import com.example.gardeningjournal.data.PlantDetailsViewModel
import com.example.gardeningjournal.data.PlantDetailsViewModelFactory
import com.example.gardeningjournal.database.InitPlantDB
import com.example.gardeningjournal.database.PlantRepo
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
class PlantDetailsFragment : Fragment() {
    private lateinit var viewModel: PlantDetailsViewModel
    private lateinit var plantName: TextView
    private lateinit var plantType: TextView
    private lateinit var wateringFrequency: TextView
    private lateinit var plantedDate: TextView


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

        val application = requireNotNull(this.activity).application
        val dataSource = InitPlantDB.getDatabase(application).plantDao()
        val repository = PlantRepo(dataSource)
        val viewModelFactory = PlantDetailsViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(PlantDetailsViewModel::class.java)

        val plantId = arguments?.getInt("position", -1)
        viewModel.getPlant(plantId).observe(viewLifecycleOwner, Observer {
            it?.let {
                plantName.text = "Plant Name: ${it.name}"
                plantType.text = "Plant Type: ${it.type}"
                wateringFrequency.text = "Needs to be watered every ${it.wateringFrequency.toString()} days."
                plantedDate.text = "Date planted: ${it.plantedDate}"
            }
        })
    }
}