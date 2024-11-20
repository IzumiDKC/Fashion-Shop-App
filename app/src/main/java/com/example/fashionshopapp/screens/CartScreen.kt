package com.example.fashionshopapp.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.example.fashionshopapp.models.CartItem
import com.example.fashionshopapp.viewmodel.CartViewModel

@Composable
fun CartScreen(cartViewModel: CartViewModel = viewModel()) {
    // Quan sát giỏ hàng từ CartViewModel
    val cart = cartViewModel.cart.value
    Log.d("CartScreen", "Giỏ hàng: ${cart.items.map { it.product.name }}")

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Giỏ hàng", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        if (cart.items.isEmpty()) {
            Text("Giỏ hàng trống")
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(cart.items) { cartItem ->
                    CartItemView(cartItem, cartViewModel)
                }
            }
        }
    }
}




@Composable
fun CartItemView(cartItem: CartItem, cartViewModel: CartViewModel) {
    val product = cartItem.product
    Row(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        Image(
            painter = rememberImagePainter(product.imageUrl),
            contentDescription = null,
            modifier = Modifier.size(100.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(text = product.name, fontWeight = FontWeight.Bold)
            Text(text = "Giá: ${product.price} VND")
            Text(text = "Số lượng: ${cartItem.quantity}")

            Row {
                Button(onClick = { cartViewModel.updateQuantity(product, cartItem.quantity - 1) }) {
                    Text("-")
                }
                Button(onClick = { cartViewModel.updateQuantity(product, cartItem.quantity + 1) }) {
                    Text("+")
                }
            }

            Button(onClick = { cartViewModel.removeFromCart(product) }) {
                Text("Xóa")
            }
        }
    }
}


