package com.example.fashionshopapp.screens

import ProductItem
import ProductRepository
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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

import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState

@Composable
fun SaleScreen(onBack: () -> Unit, onAddToCart: (Product) -> Unit) {
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
        products = productRepository.fetchProductsOnSale()
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
            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            ) {
                Button(
                    onClick = { onBack() },
                    colors = androidx.compose.material.ButtonDefaults.buttonColors(
                        backgroundColor = Color.Blue,
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(8.dp)
                ) {
                    Text(
                        text = "Quay lại",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                products.forEach { product ->
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
                Spacer(modifier = Modifier.height(80.dp))
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



