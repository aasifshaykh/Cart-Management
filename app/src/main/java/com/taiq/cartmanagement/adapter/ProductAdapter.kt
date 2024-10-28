package com.taiq.cartmanagement.adapter

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.taiq.cartmanagement.CartSummaryActivity
import com.taiq.cartmanagement.R
import com.taiq.cartmanagement.common.CartPreferencesManager
import com.taiq.cartmanagement.databinding.ItemProductBinding
import com.taiq.cartmanagement.models.Product
import com.taiq.cartmanagement.models.Service

class ProductAdapter(
    private val context: Context,
    private val products: List<Product>,
    private val serviceList: List<Service>
    ) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

        private val cartPreferencesManager = CartPreferencesManager(context)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int = products.size

    inner class ProductViewHolder(private val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.productTitle.text = product.product_name


            Glide.with(binding.root.context)
                .load(product.product_photo)
                .placeholder(R.drawable.circle_background)
                .error(R.drawable.error_image)
                .into(binding.productImage)


            binding.addButton.setOnClickListener {
                showServiceDialog(serviceList, product);
            }
        }
        private fun showServiceDialog(services: List<Service>, product: Product) {
            val dialog = Dialog(context)
            dialog.setContentView(R.layout.dialog_services)
            dialog.setCancelable(true)

            val recyclerView = dialog.findViewById<RecyclerView>(R.id.rvServices)
            val backBtn = dialog.findViewById<ImageView>(R.id.back_button)
            val cartBtn = dialog.findViewById<ImageView>(R.id.cart_button)

            val submitBtn = dialog.findViewById<Button>(R.id.btnSubmit)

            backBtn.visibility = View.GONE
            cartBtn.visibility = View.GONE
            recyclerView.layoutManager = GridLayoutManager(binding.root.context, 2)


            val adapter = ServiceAdapter(services)
            recyclerView.adapter = adapter

            submitBtn.setOnClickListener {
                val selectedServices = adapter.getSelectedServices()
                cartPreferencesManager.saveSelectedServices(product.product_name, selectedServices)

                val intent = Intent(context, CartSummaryActivity::class.java)
                context.startActivity(intent)

                dialog.dismiss()
            }


            dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

            dialog.show()
        }


    }
}

