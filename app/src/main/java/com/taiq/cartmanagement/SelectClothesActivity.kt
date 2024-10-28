package com.taiq.cartmanagement

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import com.taiq.cartmanagement.adapter.SubCategoryAdapter
import com.taiq.cartmanagement.common.Utils
import com.taiq.cartmanagement.databinding.ActivitySelectClothesBinding
import com.taiq.cartmanagement.models.CartResponse
import com.taiq.cartmanagement.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SelectClothesActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySelectClothesBinding
    private var loaderDialog: AlertDialog? = null
    private var backPressedOnce = false
    private val backPressTimeThreshold: Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        super.onCreate(savedInstanceState)

        binding = ActivitySelectClothesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbarTitle = binding.root.findViewById<TextView>(R.id.toolbar_title)
        toolbarTitle.text = "Select Clothes"

        val backButton = binding.root.findViewById<ImageView>(R.id.back_button)
        backButton.setOnClickListener {
            handleBackPress() // Call back press handler when the toolbar back button is pressed
        }

        val cartButton = binding.root.findViewById<ImageView>(R.id.cart_button)

        cartButton.setOnClickListener {
            val intent = Intent(this, CartSummaryActivity::class.java)
            startActivity(intent)
        }
        if (Utils.internetCheck(this)) {
            fetchCartData()
        } else {
            Utils.showSnackbar(binding.root, "No Internet Connectivity")
        }
        Utils.internetCheck(this)

    }

    private fun fetchCartData() {
        loaderDialog = Utils.showLoader(this)

        val call = RetrofitInstance.api.getCartResponse()
        call.enqueue(object : Callback<CartResponse> {
            override fun onResponse(call: Call<CartResponse>, response: Response<CartResponse>) {
                Utils.hideLoader(loaderDialog)

                if (response.isSuccessful) {
                    val cartResponse = response.body()
                    cartResponse?.let {
                        if (it.sub_categories.isNotEmpty()) {

                            binding.rvCategories.layoutManager = LinearLayoutManager(this@SelectClothesActivity)
                            val adapter = SubCategoryAdapter(this@SelectClothesActivity, it.sub_categories, it.products, it.services)

                            binding.rvCategories.adapter = adapter
                        } else {
                            Utils.showToast(this@SelectClothesActivity, "No sub-categories available")
                        }

                    }
                } else {
                    Utils.showToast(this@SelectClothesActivity, "Failed to fetch data")
                }
            }

            override fun onFailure(call: Call<CartResponse>, t: Throwable) {
                Utils.hideLoader(loaderDialog)
                Utils.showToast(this@SelectClothesActivity, "Error: ${t.message}")
            }
        })
    }
    override fun onBackPressed() {
        handleBackPress()
    }

    private fun handleBackPress() {
        if (backPressedOnce) {
            finishAffinity()
        } else {
            this.backPressedOnce = true
            Utils.showToast(this@SelectClothesActivity, "Press again to exit")

            Handler(Looper.getMainLooper()).postDelayed({
                backPressedOnce = false
            }, backPressTimeThreshold)
        }
    }
}