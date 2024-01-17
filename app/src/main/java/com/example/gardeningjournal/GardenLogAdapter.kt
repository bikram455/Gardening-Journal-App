package com.example.gardeningjournal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gardeningjournal.database.PlantEntity

class GardenLogAdapter(private val plants: List<PlantEntity>?, private val listener: (Int) -> Unit) : RecyclerView.Adapter<GardenLogAdapter.GardenLogViewHolder>() {
    override fun onBindViewHolder(holder: GardenLogViewHolder, position: Int) {
        val _plant = plants?.get(position)
        if (_plant != null) {
            holder.plantTitle.text = _plant.name
        }

        holder.itemView.setOnClickListener {
            _plant?.id!!.let { it1 -> listener(it1) }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GardenLogViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.plant_layout, parent, false)
        return GardenLogViewHolder(view)
    }

    override fun getItemCount(): Int {
        if(plants == null) return  0
        return plants.size
    }


    class GardenLogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var plantTitle = itemView.findViewById<TextView>(R.id.plantTitle)
    }
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}