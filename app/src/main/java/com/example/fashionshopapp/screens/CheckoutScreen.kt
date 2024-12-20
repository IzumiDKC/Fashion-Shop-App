package com.example.fashionshopapp.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.fashionshopapp.models.CartItem
import com.example.fashionshopapp.models.CreateOrderRequest
import com.example.fashionshopapp.models.OrderDetailRequest
import com.example.fashionshopapp.viewmodel.OrderViewModel
import com.example.fashionshopapp.viewmodel.ProfileViewModel

@Composable
fun CheckoutScreen(
    totalPrice: Double,
    cartItems: List<CartItem>,
    shippingAddress: String,
    notes: String?,
    orderViewModel: OrderViewModel,
    profileViewModel: ProfileViewModel,
    onConfirmPayment: (String) -> Unit,
    onBack: () -> Unit,
    userId: String
) {
    var selectedPaymentMethod by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current
    var userShippingAddress by remember { mutableStateOf(shippingAddress) }
    var userNotes by remember { mutableStateOf(notes ?: "") }

    LaunchedEffect(userId) {
        profileViewModel.getProfile { profile ->
            profile?.let {
                userShippingAddress = it.address
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Thanh Toán",
            style = MaterialTheme.typography.h5.copy(color = MaterialTheme.colors.primary),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Text("Địa chỉ: $userShippingAddress", style = MaterialTheme.typography.body1)

        Column {
            TextField(
                value = userNotes,
                onValueChange = { userNotes = it },
                placeholder = { Text("Nhập ghi chú cho đơn hàng") },
                modifier = Modifier.fillMaxWidth().height(100.dp),
                maxLines = 5,
                singleLine = false,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color(0xFFF0F0F0),
                    focusedIndicatorColor = MaterialTheme.colors.primary,
                    cursorColor = MaterialTheme.colors.primary
                )
            )
        }

        // Tổng cộng
        Text(
            text = "Tổng cộng: ${String.format("%.3f", totalPrice)} VND",
            style = MaterialTheme.typography.h6.copy(
                color = Color.Red,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        PaymentMethods(
            selectedPaymentMethod = selectedPaymentMethod,
            onSelectPaymentMethod = { selectedPaymentMethod = it }
        )

        Button(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary)
        ) {
            Text("Quay lại giỏ hàng")
        }
    }

    selectedPaymentMethod?.let { method ->
        AlertDialog(
            onDismissRequest = { selectedPaymentMethod = null },
            title = { Text("Xác nhận thanh toán", color = MaterialTheme.colors.primary) },
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
                            shippingAddress = userShippingAddress,
                            notes = userNotes,
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
                    Text("Đồng ý", color = MaterialTheme.colors.primary)
                }
            },
            dismissButton = {
                TextButton(onClick = { selectedPaymentMethod = null }) {
                    Text("Trở về", color = MaterialTheme.colors.secondary)
                }
            }
        )
    }
}

@Composable
fun PaymentMethods(
    selectedPaymentMethod: String?,
    onSelectPaymentMethod: (String) -> Unit
) {
    Column {
        Text("Chọn phương thức thanh toán:", style = MaterialTheme.typography.subtitle1)

        PaymentButton(
            text = "Tiền mặt khi nhận hàng",
            onClick = { onSelectPaymentMethod("Thanh toán bằng tiền mặt") },
            buttonColor = Color(0xFF4CAF50),
            textColor = Color.White
        )

        PaymentButton(
            text = "Thẻ tín dụng / Thẻ ghi nợ",
            onClick = { onSelectPaymentMethod("Thanh toán bằng thẻ tín dụng") },
            buttonColor = Color(0xFF2196F3),
            textColor = Color.White
        )

        PaymentButton(
            text = "Ví điện tử (Momo, ZaloPay)",
            onClick = { onSelectPaymentMethod("Thanh toán qua ví điện tử") },
            buttonColor = Color(0xFFFFC107),
            textColor = Color.Black
        )
    }
}

@Composable
fun PaymentButton(
    text: String,
    onClick: () -> Unit,
    buttonColor: Color,
    textColor: Color
) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = buttonColor,
            contentColor = textColor
        )
    ) {
        Text(text)
    }
}

