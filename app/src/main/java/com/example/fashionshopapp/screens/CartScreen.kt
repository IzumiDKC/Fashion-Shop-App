package com.example.fashionshopapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.fashionshopapp.viewmodel.CartViewModel
import com.example.fashionshopapp.model.CartItem

@Composable
fun CartScreen(viewModel: CartViewModel) {
    val cartItems = viewModel.cartItems.value
    val totalPrice = viewModel.totalPrice.value

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        Text(
            text = "Giỏ Hàng",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp),
        )

        if (cartItems.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                Text(
                    text = "Giỏ hàng của bạn trống...",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(cartItems.size) { index ->
                    val item = cartItems[index]
                    CartItemRow(item, onRemove = { viewModel.removeItemFromCart(item) })
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Tổng cộng: ${"%.2f".format(totalPrice)} VND",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}



@Composable
fun CartItemRow(item: CartItem, onRemove: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(item.name, style = MaterialTheme.typography.bodyLarge)
            Text("Số lượng: ${item.quantity}", style = MaterialTheme.typography.bodyMedium)
        }
        Column(horizontalAlignment = androidx.compose.ui.Alignment.End) {
            Text("${item.price} VND", style = MaterialTheme.typography.bodyLarge)
            TextButton(onClick = onRemove) {
                Text("Xóa")
            }
        }
    }
}

