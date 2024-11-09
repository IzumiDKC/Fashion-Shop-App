package com.example.fashionshopapp.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.rememberImagePainter
import com.example.fashionshopapp.R

@Composable
fun AppBackground(content: @Composable () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Đặt hình nền chiếm toàn bộ màn hình
        Image(
            painter = rememberImagePainter(R.drawable.background), // Thay `your_background_image` bằng tên ảnh của bạn
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer { alpha = 0.8f }, // Điều chỉnh độ trong suốt (opacity) nếu cần
            contentScale = ContentScale.Crop // Giúp hình nền bao phủ toàn bộ màn hình
        )

        // Nội dung của màn hình
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            content()
        }
    }
}
