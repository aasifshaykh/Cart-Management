package com.taiq.cartmanagement.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.taiq.cartmanagement.R
import com.taiq.cartmanagement.models.Service

class ServiceSummaryAdapter(private val services: List<Service>) : RecyclerView.Adapter<ServiceSummaryAdapter.ServiceViewHolder>() {

    inner class ServiceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val serviceName: TextView = itemView.findViewById(R.id.serviceName)
        private val serviceAmount: TextView = itemView.findViewById(R.id.serviceAmount)

        fun bind(serviceItem: Service) {
            serviceName.text = serviceItem.service_name
            serviceAmount.text = serviceItem.service_base_price.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_service_summary, parent, false)
        return ServiceViewHolder(view)
    }

    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        holder.bind(services[position])
    }

    override fun getItemCount(): Int = services.size
}
