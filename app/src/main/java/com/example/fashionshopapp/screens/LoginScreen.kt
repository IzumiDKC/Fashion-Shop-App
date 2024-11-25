package com.example.fashionshopapp.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.fashionshopapp.viewmodel.ProfileViewModel

@Composable
fun LoginScreen(viewModel: ProfileViewModel, navController: NavController) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loginError by remember { mutableStateOf(false) }

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
                    if (success) {
                        navController.navigate("profile") { popUpTo("profile") { inclusive = true } }
                    }
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
                navController.navigate("register")
            }
        )
    }
}
