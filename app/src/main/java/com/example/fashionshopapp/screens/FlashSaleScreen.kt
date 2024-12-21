package com.example.fashionshopapp.screens

import ProductItem
import ProductRepository
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.fashionshopapp.models.Brand
import com.example.fashionshopapp.models.Category
import com.example.fashionshopapp.models.Product
import com.example.fashionshopapp.repository.BrandRepository
import com.example.fashionshopapp.repository.CategoryRepository
import com.example.fashionshopapp.utils.AppBackground
import kotlinx.coroutines.delay

@Composable
fun FlashSaleScreen(onBack: () -> Unit, onAddToCart: (Product) -> Unit, description: String) {
    var products by remember { mutableStateOf<List<Product>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var successMessage by remember { mutableStateOf<String?>(null) }
    var brands by remember { mutableStateOf<List<Brand>>(emptyList()) }
    var categories by remember { mutableStateOf<List<Category>>(emptyList()) }

    val productRepository = ProductRepository()
    val brandRepository = BrandRepository()
    val categoryRepository = CategoryRepository()

    LaunchedEffect(Unit) {
        isLoading = true
        products = productRepository.fetchFlashSaleProducts()
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
                    CircularProgressIndicator(color = Color.Blue, modifier = Modifier.padding(8.dp))
                    Text(text = "Đang tải sản phẩm...", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Blue)
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
                            androidx.compose.material.Text(
                                text = "Quay lại",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
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

                items(products.filter { product ->
                    val timeRemaining = product.hotEndDate?.let { calculateTimeRemaining(it) }
                    timeRemaining != "Sale đã kết thúc" // Filtter
                }) { product ->
                    var timeRemaining by remember { mutableStateOf(calculateTimeRemaining(product.hotEndDate.toString())) }

                    LaunchedEffect(product.hotEndDate) {
                        while (true) {
                            delay(1000)
                            timeRemaining = calculateTimeRemaining(product.hotEndDate.toString())
                        }
                    }

                    if (timeRemaining != null) {
                        ProductItemForFlashSale(
                            product = product,
                            categories = categories,
                            brands = brands,
                            onAddToCart = {
                                onAddToCart(product)
                                successMessage = "Đã thêm ${product.name} vào giỏ hàng!"
                            },
                            timeRemaining = timeRemaining
                        )
                    }
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

@Composable
fun ProductItemForFlashSale(
    product: Product,
    brands: List<Brand>,
    categories: List<Category>,
    onAddToCart: (Product) -> Unit,
    timeRemaining: String
) {
    val brand = brands.find { it.id == product.brandId }?.name ?: "Unknown Brand"
    val category = categories.find { it.id == product.categoryId }?.name ?: "Unknown Category"

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberImagePainter(product.imageUrl),
            contentDescription = null,
            modifier = Modifier.size(100.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                text = timeRemaining,
                color = Color.Red,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(bottom = 4.dp)
            )

            Text(text = product.name, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            Text(text = "Thương hiệu: $brand", color = Color.Black)

            if (product.promotionPrice != null && product.promotionPrice > 0) {
                Text(
                    text = "Giá: ${String.format("%.3f", product.price)} VND",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    textDecoration = TextDecoration.LineThrough // Gạch
                )
                Spacer(modifier = Modifier.height(4.dp))
                // Giá khuyến mãi
                Text(
                    text = buildAnnotatedString {
                        append("Sale: ${String.format("%.3f", product.finalPrice)} VND (")
                        withStyle(style = SpanStyle(color = Color.Red)) {
                            append("KM: ${String.format("%.0f", product.promotionPrice)}%")
                        }
                        append(")")

                    },
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            } else {
                Text(
                    text = "Giá: ${String.format("%.3f", product.price)} VND",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            }

            Button(
                onClick = {
                    onAddToCart(product)
                },
            ) {
                Text(
                    text = "Thêm vào giỏ",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

fun calculateTimeRemaining(endDate: String): String {
    val endDateTime = java.time.LocalDateTime.parse(endDate)
    val currentTime = java.time.LocalDateTime.now()
    val duration = java.time.Duration.between(currentTime, endDateTime)

    if (duration.isNegative) {
        return "Sale đã kết thúc"
    }
    val days = duration.toDays()
    val hours = duration.toHours() % 24
    val minutes = duration.toMinutes() % 60
    val seconds = duration.seconds % 60

    return "%dd %dh %dm %ds".format(days, hours, minutes, seconds)
}
