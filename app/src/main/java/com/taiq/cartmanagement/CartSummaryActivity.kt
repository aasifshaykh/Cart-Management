package com.taiq.cartmanagement

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.taiq.cartmanagement.adapter.OrderSummaryAdapter
import com.taiq.cartmanagement.common.CartPreferencesManager
import com.taiq.cartmanagement.common.Utils
import com.taiq.cartmanagement.databinding.ActivityCartSummaryBinding
import com.taiq.cartmanagement.models.OrderItem


class CartSummaryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCartSummaryBinding

    private lateinit var recyclerView: RecyclerView
    private lateinit var orderSummaryAdapter: OrderSummaryAdapter
    private var orderList: MutableList<OrderItem> = mutableListOf()
    private lateinit var cartPreferencesManager: CartPreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        super.onCreate(savedInstanceState)

        binding = ActivityCartSummaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cartPreferencesManager = CartPreferencesManager(this)

        val toolbarTitle = binding.root.findViewById<TextView>(R.id.toolbar_title)
        val backButton = binding.root.findViewById<ImageView>(R.id.back_button)
        val cartButton = binding.root.findViewById<ImageView>(R.id.cart_button)

        cartButton.visibility = View.GONE

        toolbarTitle.text = "Cart Summary"

        recyclerView = findViewById(R.id.recyclerViewOrderSummary)
        recyclerView.layoutManager = LinearLayoutManager(this)
        orderSummaryAdapter = OrderSummaryAdapter(orderList, cartPreferencesManager)
        recyclerView.adapter = orderSummaryAdapter

        loadCartItems()

        backButton.setOnClickListener {
            onBackPressed()
        }
    }
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, SelectClothesActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun loadCartItems() {

        val cartItems = cartPreferencesManager.getCartItems()
        orderList.clear()

        if (cartItems.isEmpty()) {
            Utils.showToast(this, "Your cart is empty.")
            return
        }

        cartItems.forEach { (productName, services) ->
            val orderItem = OrderItem(productName, services)
            orderList.add(orderItem)
        }
        orderSummaryAdapter.notifyDataSetChanged()
    }
}