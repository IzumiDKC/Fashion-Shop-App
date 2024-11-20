package com.example.fashionshopapp.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.fashionshopapp.models.Cart
import com.example.fashionshopapp.models.CartItem
import com.example.fashionshopapp.models.Product

class CartViewModel : ViewModel() {
    var cart = mutableStateOf(Cart())

    fun addToCart(product: Product) {
        val existingItem = cart.value.items.find { it.product.id == product.id }
        if (existingItem != null) {
            existingItem.quantity += 1
        } else {
            cart.value.items.add(CartItem(product))
        }
        cart.value = cart.value.copy(items = cart.value.items)
        Log.d("CartViewModel", "Giỏ hàng: ${cart.value.items.map { it.product.name }}")
    }


    fun updateQuantity(product: Product, quantity: Int) {
        val cartItem = cart.value.items.find { it.product.id == product.id }
        cartItem?.quantity = quantity
    }

    fun removeFromCart(product: Product) {
        cart.value.items.removeAll { it.product.id == product.id }
    }
}




