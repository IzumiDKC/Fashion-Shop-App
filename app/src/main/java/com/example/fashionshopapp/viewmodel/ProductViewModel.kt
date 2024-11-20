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

    val products: StateFlow<List<Product>> = _searchQuery
        .combine(_products) { query, products ->
            if (query.isBlank()) products
            else products.filter { it.name.contains(query, ignoreCase = true) }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

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
}

