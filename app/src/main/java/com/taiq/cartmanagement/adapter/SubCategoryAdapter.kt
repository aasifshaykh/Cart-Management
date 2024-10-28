package com.taiq.cartmanagement.adapter

import android.content.Context
import android.util.Log
import com.taiq.cartmanagement.models.SubCategory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.taiq.cartmanagement.R
import com.taiq.cartmanagement.databinding.ItemCategoryBinding
import com.taiq.cartmanagement.models.Product
import com.taiq.cartmanagement.models.Service

class SubCategoryAdapter(
    private val context: Context,
    private val subCategories: List<SubCategory>,
    private val productsList: List<Product>,
    private val serviceList: List<Service>,

    ) : RecyclerView.Adapter<SubCategoryAdapter.SubCategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubCategoryViewHolder {
        Log.d("SubCategoryAdapter", "onCreateViewHolder called")
        val binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SubCategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SubCategoryViewHolder, position: Int) {
        Log.d("SubCategoryAdapter", "onBindViewHolder called for position: $position")
        val subCategory = subCategories[position]
        holder.bind(subCategory)
    }

    override fun getItemCount(): Int = subCategories.size

    inner class SubCategoryViewHolder(private val binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(subCategory: SubCategory) {
            binding.categoryTitle.text = subCategory.sub_category_name

            Glide.with(binding.root.context)
                .load(subCategory.sub_category_photo)
                .into(binding.categoryImage)


            val filteredProducts = productsList.filter { it.sub_category_id == subCategory.sub_category_id }


            binding.rvProduct.layoutManager = LinearLayoutManager(binding.root.context)
            binding.rvProduct.adapter = ProductAdapter(context, filteredProducts, serviceList)

            binding.categoryTitle.setOnClickListener {
                if (binding.rvProduct.visibility == View.VISIBLE) {
                    binding.rvProduct.visibility = View.GONE
                    binding.categoryTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_down, 0)

                } else {
                    binding.rvProduct.visibility = View.VISIBLE
                    binding.categoryTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_up, 0)

                }
            }
        }
    }
}
