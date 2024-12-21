package com.example.fashionshopapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fashionshopapp.api.RetrofitInstance
import com.example.fashionshopapp.models.Order
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HistoryOrderViewModel : ViewModel() {
    private val _orders = MutableStateFlow<List<Order>?>(null)
    val orders: StateFlow<List<Order>?> = _orders

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun fetchOrders(userId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = RetrofitInstance.api.getUserOrders(userId)
                if (response.isSuccessful) {
                    _orders.value = response.body()
                    _errorMessage.value = null
                } else {
                    _errorMessage.value = "Không thể tải đơn hàng. Vui lòng thử lại."
                }
            } catch (e: Exception) {
                _errorMessage.value = "Đã xảy ra lỗi: ${e.message}"
            }
            _isLoading.value = false
        }
    }
}

