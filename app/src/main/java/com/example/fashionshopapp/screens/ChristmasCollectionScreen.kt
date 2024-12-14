package com.example.fashionshopapp.screens

import ProductItem
import ProductRepository
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
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

@Composable
fun ChristmasCollectionScreen(onBack: () -> Unit, onAddToCart: (Product) -> Unit, description: String) {
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
        products = productRepository.fetchChristmasCollection()
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
                Text(text = "Đang tải sản phẩm...", fontSize = 18.sp, color = Color.Gray)
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 80.dp)
            ) {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = { onBack() },
                            colors = androidx.compose.material.ButtonDefaults.buttonColors(
                                backgroundColor = Color.Blue,
                                contentColor = Color.White
                            )
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


                items(products) { product ->
                    ProductItemChristmas(
                        product = product,
                        categories = categories,
                        brands = brands,
                        onAddToCart = onAddToCart
                    )
                }
            }
        }
    }
}
@Composable
fun ProductItemChristmas(
    product: Product,
    brands: List<Brand>,
    categories: List<Category>,
    onAddToCart: (Product) -> Unit
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
            Text(text = product.name, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black)
      //      Text(text = "Thương hiệu: $brand", color = Color.Black)
            Text(text = "Danh mục: $category", color = Color.Black)

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
                // ko khuyến mãi
                Text(
                    text = "Giá: ${String.format("%.3f", product.price)} VND",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            }
            Button(onClick = {
                onAddToCart(product)
            }) {
                Text(text = "Thêm vào giỏ")
            }
        }
    }
}