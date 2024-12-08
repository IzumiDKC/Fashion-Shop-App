package com.example.fashionshopapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fashionshopapp.api.RetrofitInstance
import com.example.fashionshopapp.models.Order
import com.example.fashionshopapp.viewmodel.HistoryOrderViewModel
import com.example.fashionshopapp.viewmodel.ProfileViewModel

@Composable
fun HistoryOrderScreen(
    
    viewModel: HistoryOrderViewModel = HistoryOrderViewModel()) {
    val orders = viewModel.orders
    val isLoading = viewModel.isLoading
    val errorMessage = viewModel.errorMessage

    LaunchedEffect(Unit) {
        viewModel.fetchOrders(userId = "USER_ID") // Thay USER_ID bằng ID thực tế
    }

    Column(modifier = Modifier.padding(16.dp)) {
        if (isLoading) {
            Text("Đang tải đơn hàng...")
        } else if (!errorMessage.isNullOrEmpty()) {
            Text("Lỗi: $errorMessage")
        } else if (orders.isNullOrEmpty()) {
            Text("Không có đơn hàng nào.")
        } else {
            LazyColumn {
                items(orders) { order ->
                    OrderItem(order)
                }
            }
        }
    }
}

@Composable
fun OrderItem(order: Order) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text("Mã đơn hàng: ${order.id}")
        Text("Ngày đặt: ${order.orderDate}")
        Text("Tổng tiền: ${order.totalPrice}")
        Text("Địa chỉ giao hàng: ${order.shippingAddress}")
        Text("Trạng thái: ${order.status}")
        Text("Ghi chú: ${order.notes}")
        order.orderDetails.forEach { detail ->
            Column(modifier = Modifier.padding(start = 16.dp)) {
                Text("- Sản phẩm: ${detail.name}")
                Text("  Số lượng: ${detail.quantity}")
                Text("  Giá: ${detail.finalPrice}")
            }
        }
    }
}



