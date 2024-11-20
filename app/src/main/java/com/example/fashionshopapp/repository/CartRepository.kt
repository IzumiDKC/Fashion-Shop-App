package com.example.fashionshopapp.repository

import com.example.fashionshopapp.models.CartItem
import com.example.fashionshopapp.models.Product

class CartRepository {
    private val cartItems = mutableListOf<CartItem>()

    fun getCartItems(): List<CartItem> = cartItems

    fun addToCart(product: Product) {
        val existingItem = cartItems.find { it.product.id == product.id }
        if (existingItem != null) {
            existingItem.quantity += 1
        } else {
            cartItems.add(CartItem(product))
        }
    }

    fun updateQuantity(product: Product, quantity: Int) {
        cartItems.find { it.product.id == product.id }?.let {
            it.quantity = quantity
        }
    }

    fun removeFromCart(product: Product) {
        cartItems.removeAll { it.product.id == product.id }
    }
}
