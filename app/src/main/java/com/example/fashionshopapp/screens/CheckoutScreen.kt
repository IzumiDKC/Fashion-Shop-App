package com.example.fashionshopapp.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.fashionshopapp.models.CartItem
import com.example.fashionshopapp.models.CreateOrderRequest
import com.example.fashionshopapp.models.OrderDetailRequest
import com.example.fashionshopapp.viewmodel.OrderViewModel

@Composable
fun CheckoutScreen(
    totalPrice: Double,
    cartItems: List<CartItem>,
    shippingAddress: String,
    notes: String?,
    orderViewModel: OrderViewModel,
    onConfirmPayment: (String) -> Unit,
    onBack: () -> Unit,
    userId: String
) {
    var selectedPaymentMethod by remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Thanh Toán",
            style = MaterialTheme.typography.h5,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Tổng cộng: ${String.format("%.3f", totalPrice)} VND",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Column {

            Spacer(modifier = Modifier.height(16.dp))

            Text("Chọn phương thức thanh toán:", style = MaterialTheme.typography.subtitle1)
            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { selectedPaymentMethod = "Thanh toán bằng tiền mặt" },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
            ) {
                Text("Tiền mặt khi nhận hàng")
            }

            Button(
                onClick = { selectedPaymentMethod = "Thanh toán bằng thẻ tín dụng" },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
            ) {
                Text("Thẻ tín dụng / Thẻ ghi nợ")
            }

            Button(
                onClick = { selectedPaymentMethod = "Thanh toán qua ví điện tử" },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
            ) {
                Text("Ví điện tử (Momo, ZaloPay)")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth().padding(bottom = 50.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary)
        ) {
            Text("Quay lại giỏ hàng")
        }
    }

    selectedPaymentMethod?.let { method ->
        AlertDialog(
            onDismissRequest = { selectedPaymentMethod = null },
            title = { Text("Xác nhận thanh toán") },
            text = { Text("Bạn có chắc chắn muốn sử dụng phương thức $method?") },
            confirmButton = {
                TextButton(onClick = {
                    if (userId.isNotEmpty()) {
                        val orderDetails = cartItems.map { cartItem ->
                            OrderDetailRequest(
                                productId = cartItem.product.id,
                                quantity = cartItem.quantity
                            )
                        }
                        val createOrderRequest = CreateOrderRequest(
                            userId = userId.trim(),
                            totalPrice = totalPrice,
                            shippingAddress = shippingAddress,
                            notes = notes,
                            orderDetails = orderDetails
                        )

                        orderViewModel.createOrder(createOrderRequest)
                        Toast.makeText(context, "Đã xác nhận đơn hàng!", Toast.LENGTH_SHORT).show()
                        onConfirmPayment(method)
                    } else {
                        println("Lỗi: userId không hợp lệ")
                    }
                    selectedPaymentMethod = null

                }) {
                    Text("Đồng ý")
                }
            },
            dismissButton = {
                TextButton(onClick = { selectedPaymentMethod = null }) {
                    Text("Trở về")
                }
            }
        )
    }
}

