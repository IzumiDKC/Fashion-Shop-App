package com.example.fashionshopapp.screens

import ProfileViewModel
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ProfileScreen(viewModel: ProfileViewModel, navController: NavController) {
    val isLoggedIn by viewModel.isLoggedIn.collectAsState()

    if (isLoggedIn) {
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
        LoginScreen(viewModel, navController)
    }
}
