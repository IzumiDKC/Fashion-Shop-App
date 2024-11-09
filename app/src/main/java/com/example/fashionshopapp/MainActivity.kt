package com.example.fashionshopapp

import com.example.fashionshopapp.utils.BannerCarousel
import CategoryGrid
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
import com.example.fashionshopapp.utils.AppBackground
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    AppBackground {  // add bao quanh
                        Column(modifier = Modifier.fillMaxSize()) {
                            BannerCarousel()
                            CategoryGrid()
                            Spacer(modifier = Modifier.height(16.dp))  // Khoảng trống giữa banner và sản phẩm
                            ProductScreen()
                        }
                    }
                }
            }
        }
    }
}

