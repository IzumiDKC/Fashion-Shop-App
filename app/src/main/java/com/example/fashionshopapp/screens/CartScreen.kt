package com.example.fashionshopapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.example.fashionshopapp.models.CartItem
import com.example.fashionshopapp.viewmodel.CartViewModel

@Composable
fun CartScreen(cartViewModel: CartViewModel = viewModel()) {
    val cartItems = cartViewModel.cartItems

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Giỏ Hàng", style = MaterialTheme.typography.h5)

        if (cartItems.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Giỏ hàng trống", color = Color.Gray)
            }
        } else {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(cartItems) { cartItem ->
                    CartItemRow(
                        cartItem = cartItem,
                        onQuantityChange = { quantity ->
                            cartViewModel.updateQuantity(cartItem.product, quantity)
                        },
                        onRemove = { cartViewModel.removeFromCart(cartItem.product) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            val totalPrice = cartItems.sumOf { it.product.price * it.quantity }
            Text("Tổng cộng: $totalPrice VND", style = MaterialTheme.typography.h6)

            Button(
                onClick = { /* Xử lý thanh toán */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Thanh toán")
            }
        }
    }
}

@Composable
fun CartItemRow(
    cartItem: CartItem,
    onQuantityChange: (Int) -> Unit,
    onRemove: () -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = rememberImagePainter(cartItem.product.imageUrl),
            contentDescription = null,
            modifier = Modifier.size(64.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(cartItem.product.name, style = MaterialTheme.typography.subtitle1)
            Text("Giá: ${cartItem.product.price} VND")
        }

        Spacer(modifier = Modifier.width(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { onQuantityChange(cartItem.quantity - 1) }) {
                Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Giảm số lượng")
            }

            Text(cartItem.quantity.toString())

            IconButton(onClick = { onQuantityChange(cartItem.quantity + 1) }) {
                Icon(Icons.Default.KeyboardArrowUp, contentDescription = "Tăng số lượng")
            }
        }

        IconButton(onClick = onRemove) {
            Icon(Icons.Default.Delete, contentDescription = "Xóa sản phẩm")
        }
    }
}
