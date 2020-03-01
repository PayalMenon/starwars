package com.example.starwars.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.starwars.R
import com.example.starwars.models.Vehicle
import kotlinx.android.synthetic.main.vehicle_list_item.view.*

/*
* TODO: Handle clicks on the vehicle in the list
* */
class VehicleAdapter : RecyclerView.Adapter<VehicleAdapter.VehicleViewHolder>() {

    var vehicleList : List<Vehicle> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleViewHolder {
        return VehicleViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.vehicle_list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return vehicleList.size
    }

    override fun onBindViewHolder(holder: VehicleViewHolder, position: Int) {
        holder.setupView(vehicleList[position])
    }

    fun setVehiclesList(list : List<Vehicle>) {
        vehicleList = list
    }

    class VehicleViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun setupView(vehicle: Vehicle) {
            view.item_title.text = vehicle.name
            view.item_model.text = vehicle.model
        }
    }
}