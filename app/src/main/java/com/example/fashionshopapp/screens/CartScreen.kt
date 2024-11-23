package com.example.fashionshopapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import java.text.DecimalFormat
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.fashionshopapp.models.CartItem
import com.example.fashionshopapp.viewmodel.CartViewModel

@Composable
fun CartScreen(
    navController: NavController,
    cartViewModel: CartViewModel = viewModel()
) {
    val cartItems = cartViewModel.cartItems
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Giỏ Hàng", style = MaterialTheme.typography.h5)
        if (cartItems.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Giỏ hàng trống", color = Color.Black)
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

            // Hiển thị giá tiền tổng
            val totalPrice = cartItems.sumOf { it.product.finalPrice * it.quantity }
            Text(
                text = "Tổng cộng: ${String.format("%.3f", totalPrice)} VND",
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(top = 16.dp)
            )

            // Dời nút thanh toán ra giữa bên phải
            Box(
                modifier = Modifier.fillMaxWidth().padding(bottom = 50.dp),
                contentAlignment = Alignment.CenterEnd// Căn chỉnh nút về bên phải
            ) {
                Button(
                    onClick = { navController.navigate("checkout/${totalPrice}") }
                ) {
                    Text("Thanh toán")
                }
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
            Text("Giá: ${String.format("%.3f", cartItem.product.finalPrice)} VND")
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
