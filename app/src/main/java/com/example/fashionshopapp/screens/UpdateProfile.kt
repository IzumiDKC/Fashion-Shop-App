package com.example.fashionshopapp.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fashionshopapp.models.UpdatedProfileModel
import com.example.fashionshopapp.viewmodel.ProfileViewModel

@Composable
fun UpdateProfile(viewModel: ProfileViewModel, navController: NavController, userId: String) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("Chưa cập nhật") }
    var fullName by remember { mutableStateOf("Chưa cập nhật") }
    var age by remember { mutableStateOf("Chưa cập nhật") }
    var address by remember { mutableStateOf("Chưa cập nhật") }
    var phoneNumber by remember { mutableStateOf("Chưa cập nhật") }
    var showError by remember { mutableStateOf(false) }


    LaunchedEffect(userId) {
        viewModel.getProfile { profile ->
            profile?.let {
                username = it.username
                email = it.email ?: "Chưa cập nhật"
                fullName = it.fullName ?: "Chưa cập nhật"
                age = it.age ?: "Chưa cập nhật"
                address = it.address ?: "Chưa cập nhật"
                phoneNumber = it.phoneNumber ?: "Chưa cập nhật"
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TopAppBar(
            title = { Text("Cập nhật thông tin cá nhân") },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                }
            },
            backgroundColor = MaterialTheme.colors.primary,
            contentColor = Color.White
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = username,
            onValueChange = {}, // Không cho đổi
            label = {
                Text(
                    "Tên đăng nhập",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp),
            enabled = false, // Lock
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color(0xFFF0F0F0))
        )

        TextField(
            value = email,
            onValueChange = { email = it },
            label = {
                Text(
                    "Email",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color(0xFFF0F0F0))
        )

        TextField(
            value = fullName,
            onValueChange = { fullName = it },
            label = {
                Text(
                    "Họ và tên:",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color(0xFFF0F0F0))
        )

        TextField(
            value = age,
            onValueChange = { age = it },
            label = {
                Text(
                    "Tuổi:",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color(0xFFF0F0F0))
        )

        TextField(
            value = address,
            onValueChange = { address = it },
            label = {
                Text(
                    "Địa chỉ:",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color(0xFFF0F0F0))
        )

        TextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = {
                Text(
                    "Số điện thoại:",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color(0xFFF0F0F0))
        )

        Button(
            onClick = {
                val updatedProfile = UpdatedProfileModel(username, email, fullName, age, address, phoneNumber)
                viewModel.updateProfile(userId, updatedProfile) { success ->
                    if (success) {
                        navController.popBackStack()
                        Log.e("UpdateProfile", "Cập nhật thành công")

                    } else {
                        Log.e("UpdateProfile", "Cập nhật thất bại")
                        showError = true
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Cập nhật", color = MaterialTheme.colors.onPrimary)
        }

        if (showError) {
            Snackbar(
                action = {
                    TextButton(onClick = { showError = false }) {
                        Text("Đóng", color = Color.White)
                    }
                },
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Cập nhật thất bại. Vui lòng thử lại sau.", color = Color.White)
            }
        }
    }
}
