package com.example.fashionshopapp.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fashionshopapp.models.CreateOrderRequest
import com.example.fashionshopapp.models.Order
import com.example.fashionshopapp.repository.OrderRepository
import kotlinx.coroutines.launch

class OrderViewModel(private val cartViewModel: CartViewModel): ViewModel() {
    private val orderRepository = OrderRepository()

    private val _orderResult = MutableLiveData<Order?>()
    val orderResult: LiveData<Order?> get() = _orderResult

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    fun createOrder(createOrderRequest: CreateOrderRequest) {
        viewModelScope.launch {
            try {
                val response = orderRepository.createOrder(createOrderRequest)

                if (response.isSuccessful) {
                    // Đơn hàng được tạo thành công, cập nhật vào _orderResult
                    _orderResult.value = response.body()
                    _error.value = null // Reset lỗi nếu có
                    cartViewModel.clearCart()
                } else {
                    // Xử lý lỗi từ server
                    val errorMessage = response.errorBody()?.string() ?: "Có lỗi xảy ra"
                    _error.value = errorMessage
                    println("Có lỗi khi lưu đơn hàng: $errorMessage")
                }
            } catch (e: Exception) {
                // Xử lý lỗi ngoại lệ
                _error.value = e.localizedMessage ?: "Lỗi không xác định"
                println("Lỗi khi gọi API: ${e.localizedMessage}")
            }
        }
    }
}