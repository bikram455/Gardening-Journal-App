package com.example.gardeningjournal

import android.app.AlertDialog
import android.icu.util.Calendar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gardeningjournal.data.GardenLogViewModel
import com.example.gardeningjournal.data.GardenLogViewModelFactory
import com.example.gardeningjournal.database.PlantDB
import com.example.gardeningjournal.database.PlantEntity
import com.example.gardeningjournal.database.PlantRepo
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


class GardenLogFragment : Fragment(), CoroutineScope {
    private lateinit var viewModel: GardenLogViewModel
    private lateinit var fab: FloatingActionButton
    private lateinit var recyclerView: RecyclerView
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
        return inflater.inflate(R.layout.fragment_garden_log, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            GardenLogFragment().apply {
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fab =  view.findViewById<FloatingActionButton>(R.id.addLog)

        fab.setOnClickListener{
            openAddLog()
        }

        val application = requireNotNull(this.activity).application
        val dataSource = PlantDB(application).plantDao()
        val repository = PlantRepo(dataSource)
        val viewModelFactory = GardenLogViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(GardenLogViewModel::class.java)

        recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        job = Job()
        fetchPlants()
    }

    private fun fetchPlants() {
        launch {
            context?.let {
                val plants = PlantDB(it).plantDao().selectAll()
                val adapter = GardenLogAdapter(plants.toList()) { pos: Int ->
                    val action =
                        GardenLogFragmentDirections.actionGardenFragmentToPlantFragment(pos)
                    findNavController().navigate(action)
                }
                recyclerView.adapter = adapter
            }
        }
    }

    private fun navigateToDetailsFragment(position: Int) {
        val action = GardenLogFragmentDirections.actionGardenFragmentToPlantFragment(position)
        findNavController().navigate(action)
    }

    private fun openAddLog() {
        val alertDialogView = LayoutInflater.from(requireContext()).inflate(R.layout.add_garden_log, null)
        val plantName = alertDialogView.findViewById<EditText>(R.id.plant_name)
        val plantType = alertDialogView.findViewById<EditText>(R.id.plant_type)
        val wateringFrequency = alertDialogView.findViewById<EditText>(R.id.plant_watering_frequency)
        val plantedDate = alertDialogView.findViewById<CalendarView>(R.id.plantedDate)

        val calInstance = Calendar.getInstance()

        var dateValue : String = "${calInstance.get(Calendar.MONTH) + 1}-" +
                "${calInstance.get(Calendar.DATE)}-${calInstance.get(Calendar.YEAR)}"

        plantedDate.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedDate = Calendar.getInstance()
            selectedDate.set(year, month, dayOfMonth)
            dateValue = "${(month + 1)}-$dayOfMonth-$year"
        }

        val alertDialog = AlertDialog.Builder(requireContext())
            .setTitle("Add Garden Log Entry")
            .setView(alertDialogView)
            .setPositiveButton("OK") { dialog, which ->
                var plant: PlantEntity = PlantEntity(null,
                    plantName.text.toString(), plantType.text.toString(),
                    wateringFrequency.text.toString().toInt(), dateValue, calInstance.timeInMillis);
                viewModel.insertPlant(plant)
                fetchPlants()
                Snackbar.make(requireView(), "Added ${plantName.text.toString()} to garden log entry.", Snackbar.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel") { dialog, which ->
//                context?.deleteDatabase("plant_database")
                Snackbar.make(requireView(), "Garden log add modal dismissed", Snackbar.LENGTH_SHORT).show()
            }
            .setCancelable(false)
            .create()

        alertDialog.show()
    }

}