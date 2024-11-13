package com.example.fashionshopapp.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.fashionshopapp.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(viewModel: ProfileViewModel) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loginError by remember { mutableStateOf(false) }

    if (viewModel.isLoggedIn) {
        // Hiển thị nút Đăng xuất khi đã đăng nhập
        Button(onClick = { viewModel.logout() }) {
            Text("Đăng xuất")
        }
    } else {
        // Giao diện đăng nhập
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            TextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Tên đăng nhập") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Mật khẩu") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    viewModel.login(username, password) { success ->
                        loginError = !success
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Đăng nhập")
            }
            if (loginError) {
                Text("Đăng nhập không thành công", color = MaterialTheme.colorScheme.error)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Nếu chưa có tài khoản, đăng ký tại đây",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable {
                    // Xử lý điều hướng sang màn hình đăng ký
                }
            )
        }
    }
}
