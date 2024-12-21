package com.example.fashionshopapp.screens

import ProductItem
import ProductRepository
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fashionshopapp.models.Brand
import com.example.fashionshopapp.models.Category
import com.example.fashionshopapp.models.Product
import com.example.fashionshopapp.repository.BrandRepository
import com.example.fashionshopapp.repository.CategoryRepository
import com.example.fashionshopapp.utils.AppBackground

@Composable
fun HotScreen(onBack: () -> Unit, onAddToCart: (Product) -> Unit, description: String) {
    var products by remember { mutableStateOf<List<Product>>(emptyList()) }
    var brands by remember { mutableStateOf<List<Brand>>(emptyList()) }
    var categories by remember { mutableStateOf<List<Category>>(emptyList()) }
    var successMessage by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    val productRepository = ProductRepository()
    val brandRepository = BrandRepository()
    val categoryRepository = CategoryRepository()

    LaunchedEffect(Unit) {
        isLoading = true
        products = productRepository.fetchHotProducts()
        brands = brandRepository.fetchBrands()
        categories = categoryRepository.fetchCategories()
        isLoading = false
    }

    AppBackground {
        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    androidx.compose.material.CircularProgressIndicator(
                        color = Color.Blue,
                        modifier = Modifier.padding(8.dp)
                    )
                    Text(
                        text = "Đang tải sản phẩm...",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Blue
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 80.dp)
            ) {
                item {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Button(
                            onClick = { onBack() },
                            colors = androidx.compose.material.ButtonDefaults.buttonColors(
                                backgroundColor = Color.Blue,
                                contentColor = Color.White
                            ),
                            modifier = Modifier
                                .padding(8.dp)
                                .align(Alignment.CenterStart)
                        ) {
                            Text(text = "Quay lại", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                    Text(
                        text = description,
                        fontSize = 20.sp,
                        color = Color.Magenta,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.CenterHorizontally)
                    )
                }
                items(products) { product ->
                    ProductItem(
                        product = product,
                        brands = brands,
                        categories = categories,
                        onAddToCart = {
                            onAddToCart(product)
                            successMessage = "Đã thêm ${product.name} vào giỏ hàng!"
                        }
                    )
                }
            }

            if (successMessage != null) {
                LaunchedEffect(successMessage) {
                    kotlinx.coroutines.delay(2000)
                    successMessage = null
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Text(
                        text = successMessage ?: "",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .background(Color(0xFF4CAF50))
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }
            }
        }
    }
}
