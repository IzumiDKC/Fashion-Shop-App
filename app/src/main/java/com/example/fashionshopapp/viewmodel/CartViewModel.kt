package com.example.fashionshopapp.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fashionshopapp.api.ApiService
import com.example.fashionshopapp.models.CartItem
import com.example.fashionshopapp.models.CreateOrderRequest
import com.example.fashionshopapp.models.Product
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CartViewModel : ViewModel() {
    private val _cartItems = mutableStateListOf<CartItem>()
    val cartItems: List<CartItem> get() = _cartItems

    fun addToCart(product: Product) {
        val existingItem = _cartItems.find { it.product.id == product.id }

        if (existingItem != null) {
            existingItem.quantity += 1
        } else {
            _cartItems.add(CartItem(product))
        }
    }

    fun removeFromCart(product: Product) {
        _cartItems.removeAll { it.product.id == product.id }
    }

    fun updateQuantity(product: Product, quantity: Int) {
        val item = _cartItems.find { it.product.id == product.id }
        item?.let {
            if (quantity > 0) it.quantity = quantity else removeFromCart(product)
        }
    }

    fun clearCart() {
        _cartItems.clear()
    }




}
