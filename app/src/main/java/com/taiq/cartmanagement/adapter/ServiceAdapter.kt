package com.taiq.cartmanagement.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.taiq.cartmanagement.R
import com.taiq.cartmanagement.models.Service

class ServiceAdapter(private val services: List<Service>) : RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder>() {

    private val selectedServices = mutableListOf<Service>()


    inner class ServiceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val serviceName: TextView = itemView.findViewById(R.id.serviceName)
        private val serviceIcon: ImageView = itemView.findViewById(R.id.serviceIcon)

        fun bind(service: Service) {
            serviceName.text = service.service_name

            itemView.setBackgroundColor(
                if (service.isSelected) Color.parseColor("#ADD8E6")
                else Color.TRANSPARENT
            )

            itemView.setOnClickListener {
                service.isSelected = !service.isSelected
                if (service.isSelected) {
                    selectedServices.add(service)
                } else {
                    selectedServices.remove(service)
                }
                notifyItemChanged(adapterPosition)
            }

            Glide.with(itemView.context)
                .load(service.service_icon)
                .placeholder(R.drawable.circle_background)
                .error(R.drawable.error_image)
                .into(serviceIcon)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_service, parent, false)
        return ServiceViewHolder(view)
    }

    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        holder.bind(services[position])
    }

    override fun getItemCount(): Int = services.size

    fun getSelectedServices(): List<Service> = selectedServices
}
