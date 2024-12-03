package com.example.fashionshopapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fashionshopapp.models.UserProfile
import com.example.fashionshopapp.viewmodel.ProfileViewModel

@Composable
fun ProfileDetail(viewModel: ProfileViewModel, navController: NavController) {
    val profileDetail = remember { mutableStateOf<UserProfile?>(null) }

    // Fetch profile data
    LaunchedEffect(viewModel.username) {
        viewModel.getProfile { profile ->
            profileDetail.value = profile
        }
    }

    // Display profile data if available
    profileDetail.value?.let { profile ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            // Header
            Text(
                text = "Thông Tin Cá Nhân",
                style = MaterialTheme.typography.h5.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.primary
                ),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Profile Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape(16.dp),
                backgroundColor = MaterialTheme.colors.surface,
                elevation = 4.dp
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    ProfileDetailItem(label = "Tên người dùng", value = profile.username ?: "Không có")
                    ProfileDetailItem(label = "Email", value = profile.email ?: "Không có")
                    ProfileDetailItem(label = "Họ và tên", value = profile.fullName ?: "Không có")
                    ProfileDetailItem(label = "Tuổi", value = profile.age?.toString() ?: "Không có")
                    ProfileDetailItem(label = "Địa chỉ", value = profile.address ?: "Không có")
                    ProfileDetailItem(label = "Số điện thoại", value = profile.phoneNumber ?: "Không có")
                }

            }
        }
    } ?: run {
        // Loading or error state
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Đang tải dữ liệu...",
                style = MaterialTheme.typography.body1.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray
                )
            )
        }
    }
}

@Composable
fun ProfileDetailItem(label: String, value: String) {
    Column(
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        Text(
            text = label,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = Color.Gray
        )
        Text(
            text = value,
            fontSize = 16.sp,
            color = MaterialTheme.colors.onSurface
        )
    }
}
