package com.example.fashionshopapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CheckoutScreen(
    totalPrice: Double,
    onConfirmPayment: (String) -> Unit,
    onBack: () -> Unit
) {
    // State để lưu trữ phương thức thanh toán đã chọn
    var selectedPaymentMethod by remember { mutableStateOf<String?>(null) }

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

    // Dialog xác nhận cho từng phương thức thanh toán
    selectedPaymentMethod?.let { method ->
        AlertDialog(
            onDismissRequest = { selectedPaymentMethod = null },
            title = { Text("Xác nhận thanh toán") },
            text = { Text("Bạn có chắc chắn muốn sử dụng phương thức $method?") },
            confirmButton = {
                TextButton(onClick = {
                    onConfirmPayment(method)
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
