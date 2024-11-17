package com.example.fashionshopapp.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.fashionshopapp.models.Product
import com.example.fashionshopapp.model.CartItem

class CartViewModel : ViewModel() {

    // Danh sách các sản phẩm trong giỏ hàng
    private val _cartItems = mutableStateOf<List<CartItem>>(emptyList())
    val cartItems = _cartItems

    // Tổng giá trị của giỏ hàng
    private val _totalPrice = mutableStateOf(0.0)
    val totalPrice = _totalPrice

    // Thêm sản phẩm vào giỏ hàng
    fun addProductToCart(product: Product) {
        val existingItem = _cartItems.value.find { it.id == product.id.toString() }
        if (existingItem != null) {
            // Nếu sản phẩm đã có trong giỏ, tăng số lượng
            val updatedItems = _cartItems.value.map {
                if (it.id == product.id.toString()) it.copy(quantity = it.quantity + 1) else it
            }
            _cartItems.value = updatedItems
        } else {
            // Nếu sản phẩm chưa có trong giỏ, thêm sản phẩm mới
            val newItem = CartItem(
                id = product.id.toString(),
                name = product.name,
                price = product.price,
                quantity = 1,
                product = product
            )
            _cartItems.value = _cartItems.value + newItem
        }
        updateTotalPrice()
    }

    // Xóa sản phẩm khỏi giỏ hàng
    fun removeItemFromCart(item: CartItem) {
        _cartItems.value = _cartItems.value.filterNot { it == item }
        updateTotalPrice()
    }

    // Cập nhật tổng giá trị của giỏ hàng
    private fun updateTotalPrice() {
        _totalPrice.value = _cartItems.value.sumOf { it.price * it.quantity }
    }
}
