package com.taiq.cartmanagement.common

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.taiq.cartmanagement.models.Service

class CartPreferencesManager(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("CartPreferences", Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()
    private val gson = Gson()

    fun saveSelectedServices(productName: String, selectedServices: List<Service>) {
        val servicesJson = gson.toJson(selectedServices)
        editor.putString(productName, servicesJson)
        editor.apply()
    }

    fun getCartItems(): Map<String, List<Service>> {
        val map = mutableMapOf<String, List<Service>>()

        if (sharedPreferences.all.isEmpty()) {
            return map
        }

        sharedPreferences.all.forEach { entry ->
            val productName = entry.key
            val entryValue = entry.value

            try {

                if (entryValue is String) {
                    val servicesJson = entryValue
                    val services: List<Service> = gson.fromJson(servicesJson, object : TypeToken<List<Service>>() {}.type)
                    map[productName] = services
                } else if (entryValue is Set<*>) {
                }
            } catch (e: Exception) {
                // Handle potential JSON parsing errors or unexpected data format
                e.printStackTrace()
            }
        }

        return map
    }


    fun removeOrderItem(productName: String) {
        editor.remove(productName).apply()
    }

    fun clearCart() {
        editor.clear().apply()
    }
}
