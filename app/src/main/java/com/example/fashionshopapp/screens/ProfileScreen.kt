package com.example.fashionshopapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.fashionshopapp.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(viewModel: ProfileViewModel, navController: NavController) {
    if (viewModel.isLoggedIn) {
        // Hiển thị giao diện hồ sơ khi đã đăng nhập
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text("Xin chào, ${viewModel.username ?: "Người dùng"}!")
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { viewModel.logout() }) {
                Text("Đăng xuất")
            }
        }
    } else {
        // Chuyển hướng đến màn hình đăng nhập nếu chưa đăng nhập
        LoginScreen(viewModel, navController)
    }
}
