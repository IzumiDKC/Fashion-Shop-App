package com.example.fashionshopapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fashionshopapp.api.RetrofitInstance
import com.example.fashionshopapp.models.Order
import kotlinx.coroutines.launch

class HistoryOrderViewModel : ViewModel() {
    var orders by mutableStateOf<List<Order>?>(null)
    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)

    fun fetchOrders(userId: String) {
        viewModelScope.launch {
            isLoading = true
            try {
                val response = RetrofitInstance.api.getUserOrders(userId)
                if (response.isSuccessful) {
                    orders = response.body()
                    errorMessage = null
                } else {
                    errorMessage = "Không thể tải đơn hàng. Vui lòng thử lại."
                }
            } catch (e: Exception) {
                errorMessage = "Đã xảy ra lỗi: ${e.message}"
            }
            isLoading = false
        }
    }
}
