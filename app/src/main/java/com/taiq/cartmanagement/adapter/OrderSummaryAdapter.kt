package com.taiq.cartmanagement.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.taiq.cartmanagement.R
import com.taiq.cartmanagement.common.CartPreferencesManager
import com.taiq.cartmanagement.models.OrderItem

class OrderSummaryAdapter(
    private val orderList: MutableList<OrderItem>,
    private val cartPreferencesManager: CartPreferencesManager
) : RecyclerView.Adapter<OrderSummaryAdapter.OrderSummaryViewHolder>() {

    inner class OrderSummaryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val productName: TextView = itemView.findViewById(R.id.productName)
        private val recyclerViewServices: RecyclerView = itemView.findViewById(R.id.recyclerViewServices)
        private val deleteButton: Button = itemView.findViewById(R.id.deleteButton)

        fun bind(orderItem: OrderItem) {
            productName.text = orderItem.productName

            recyclerViewServices.layoutManager = LinearLayoutManager(itemView.context)
            val serviceAdapter = ServiceSummaryAdapter(orderItem.services)
            recyclerViewServices.adapter = serviceAdapter

            deleteButton.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    removeItem(position)
                    cartPreferencesManager.removeOrderItem(orderItem.productName)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderSummaryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_order_summary, parent, false)
        return OrderSummaryViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderSummaryViewHolder, position: Int) {
        holder.bind(orderList[position])
    }

    override fun getItemCount(): Int = orderList.size


    fun removeItem(position: Int) {
        orderList.removeAt(position)
        notifyItemRemoved(position)
    }
}
