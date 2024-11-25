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
fun RegisterScreen(viewModel: ProfileViewModel, navController: NavController) {
    var username by remember { mutableStateOf("") }
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessages by remember { mutableStateOf<List<String>?>(null) }

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
            value = fullName,
            onValueChange = { fullName = it },
            label = { Text("Họ tên") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
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
                viewModel.register(username, fullName, email, password) { success, errors ->
                    if (!success) {
                        errorMessages = errors
                    } else {
                        navController.navigate("login") { popUpTo("login") { inclusive = true } }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Đăng ký")
        }

        errorMessages?.let { errors ->
            Spacer(modifier = Modifier.height(8.dp))
            Column {
                errors.forEach { error ->
                    Text(text = error, color = MaterialTheme.colorScheme.error)
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Đã có tài khoản? Đăng nhập tại đây",
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.clickable { navController.navigate("login") }
        )
    }
}
