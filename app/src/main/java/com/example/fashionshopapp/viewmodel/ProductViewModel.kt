package com.example.fashionshopapp.viewmodel

import ProductRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fashionshopapp.models.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

import kotlinx.coroutines.flow.*

class ProductViewModel : ViewModel() {
    private val productRepository = ProductRepository()
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    private val _searchQuery = MutableStateFlow("")
    //Moi
    private val _filteredProducts = MutableStateFlow<List<Product>>(emptyList()) // Danh sách sản phẩm đã lọc

    val products: StateFlow<List<Product>> = _searchQuery
        .combine(_products) { query, products ->
            if (query.isBlank()) products
            else products.filter { it.name.contains(query, ignoreCase = true) }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    //Moi tiep
    val filteredProducts: StateFlow<List<Product>> = _filteredProducts

    init {
        fetchProducts()
    }
    private fun fetchProducts() {
        viewModelScope.launch {
            _products.value = productRepository.fetchProducts()
        }
    }

    fun searchProducts(query: String) {
        _searchQuery.value = query
    }

    /*val categoryMap = mapOf(
        1 to "Áo sơ mi",
        2 to "Quần jean",
        3 to "Áo phông",
        4 to "Giày",
        5 to "Váy",
        6 to "Đồ bộ",
        7 to "Áo ấm",
        8 to "Quần short",
        9 to "Tất",
        10 to "Cà vạt",
        11 to "Kẹp cà vạt"
    )

    fun filterProductsByLabels(labels: List<String>) {
        val filtered = _products.value.filter { product ->
            val categoryName = categoryMap[product.categoryId] ?: "" // Giả sử mỗi sản phẩm có thuộc tính categoryName
            labels.any { label -> categoryName.contains(label, ignoreCase = true) }
        }
        _filteredProducts.value = filtered
    }*/
}
