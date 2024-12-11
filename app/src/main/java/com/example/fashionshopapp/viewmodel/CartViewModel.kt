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

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class CartViewModel : ViewModel() {
    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> get() = _cartItems

    fun addToCart(product: Product) {
        _cartItems.update { currentItems ->
            val existingItem = currentItems.find { it.product.id == product.id }
            if (existingItem != null) {
                currentItems.map {
                    if (it.product.id == product.id) it.copy(quantity = it.quantity + 1) else it
                }
            } else {
                currentItems + CartItem(product, quantity = 1)
            }
        }
    }

    fun removeFromCart(product: Product) {
        _cartItems.update { currentItems ->
            currentItems.filter { it.product.id != product.id }
        }
    }

    fun updateQuantity(product: Product, quantity: Int) {
        _cartItems.update { currentItems ->
            if (quantity > 0) {
                currentItems.map {
                    if (it.product.id == product.id) it.copy(quantity = quantity) else it
                }
            } else {
                currentItems.filter { it.product.id != product.id }
            }
        }
    }

    fun clearCart() {
        _cartItems.value = emptyList()
    }
}

