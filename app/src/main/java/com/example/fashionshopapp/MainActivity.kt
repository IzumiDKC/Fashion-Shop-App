package com.example.fashionshopapp

import BannerCarousel
import ProductScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fashionshopapp.ui.theme.FashionShopAppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface {
                    Column(modifier = Modifier.fillMaxSize()) {
                        BannerCarousel() // Đặt banner ở đầu trang
                        Spacer(modifier = Modifier.height(16.dp)) // Khoảng trống giữa banner và nội dung
                        ProductScreen() // Danh sách sản phẩm bên dưới
                    }
                }
            }
        }
    }
}

