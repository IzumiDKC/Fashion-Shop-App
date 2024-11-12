package com.example.fashionshopapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.fashionshopapp.utils.AppBackground

@Composable
fun ProfileScreen(isLoggedIn: Boolean, onLoginClick: () -> Unit, onLogoutClick: () -> Unit) {
    AppBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            if (!isLoggedIn) {
                Button(
                    onClick = onLoginClick,
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(top = 16.dp)
                ) {
                    Text("Đăng nhập")
                }
            } else {
                Text("Xin chào, [Tên người dùng]")
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = onLogoutClick,
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(top = 16.dp)
                ) {
                    Text("Đăng xuất")
                }
                // Các thành phần hồ sơ khác khi người dùng đã đăng nhập
            }
        }
    }
}
