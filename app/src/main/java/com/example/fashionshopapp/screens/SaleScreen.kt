package com.example.fashionshopapp.screens

import ProductItem
import ProductRepository
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.Modifier
import com.example.fashionshopapp.models.Brand
import com.example.fashionshopapp.models.Category
import com.example.fashionshopapp.models.Product
import com.example.fashionshopapp.repository.BrandRepository
import com.example.fashionshopapp.repository.CategoryRepository
import com.example.fashionshopapp.utils.AppBackground

@Composable
fun SaleScreen(onBack: () -> Unit) {
    var products by remember { mutableStateOf<List<Product>>(emptyList()) }
    var brands by remember { mutableStateOf<List<Brand>>(emptyList()) }
    var categories by remember { mutableStateOf<List<Category>>(emptyList()) }

    val productRepository = ProductRepository()
    val brandRepository = BrandRepository()
    val categoryRepository = CategoryRepository()

    LaunchedEffect(Unit) {
        products = productRepository.fetchProductsOnSale()
        brands = brandRepository.fetchBrands()
        categories = categoryRepository.fetchCategories()
    }

    AppBackground {
        Column {
            Button(onClick = { onBack() }) {
                Text("Quay lại")
            }
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(products) { product ->
                    ProductItem(
                        product = product,
                        brands = brands,
                        categories = categories,
                        onAddToCart = { }
                    )
                }
            }
        }
    }
}

