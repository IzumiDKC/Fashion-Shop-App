package com.example.fashionshopapp.screens

import com.example.fashionshopapp.viewmodel.ProfileViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController


@Composable
fun ProfileOptionCard(
    title: String,
    description: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier // Thêm tham số modifier
) {
    Card(
        modifier = modifier // Sử dụng modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = title, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = description, style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
fun ProfileScreen(viewModel: ProfileViewModel, navController: NavController) {
    val isLoggedIn by viewModel.isLoggedIn.collectAsState()

    // Giao diện SwipeRefresh
        if (isLoggedIn) {
            // Nội dung cuộn
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()), // Kéo lên xuống
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Xin chào, ${viewModel.username ?: "Người dùng"}!",
                    style = MaterialTheme.typography.headlineMedium
                )
                Spacer(modifier = Modifier.height(16.dp))


                Spacer(modifier = Modifier.height(24.dp))

                ProfileOptionCard(
                    title = "Thông tin cá nhân",
                    description = "Xem và cập nhật thông tin của bạn.",
                    onClick = {
                        navController.navigate("profile_detail")
                    }
                )

                ProfileOptionCard(
                    title = "Lịch sử đơn hàng",
                    description = "Xem lại các đơn hàng đã đặt.",
                    onClick = {
                        // TODO: Chuyển sang màn hình lịch sử đơn hàng
                    }
                )
                ProfileOptionCard(
                    title = "Quản lý địa chỉ",
                    description = "Cập nhật hoặc thêm địa chỉ mới.",
                    onClick = {
                        // TODO: Chuyển sang màn hình quản lý địa chỉ
                    }
                )
                ProfileOptionCard(
                    title = "Phương thức thanh toán",
                    description = "Quản lý các phương thức thanh toán.",
                    onClick = {
                        // TODO: Chuyển sang màn hình phương thức thanh toán
                    }
                )
                Spacer(modifier = Modifier.weight(1f))

                ProfileOptionCard(
                    title = "Trung tâm hỗ trợ",
                    description = "Liên hệ hỗ trợ nếu cần giúp đỡ.",
                    onClick = {
                        // TODO: Chuyển sang màn hình hỗ trợ
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 50.dp)
                )

                Button(
                    onClick = { viewModel.logout() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Đăng xuất")
                }

            }
        } else {
            LoginScreen(viewModel, navController)
        }
    }
