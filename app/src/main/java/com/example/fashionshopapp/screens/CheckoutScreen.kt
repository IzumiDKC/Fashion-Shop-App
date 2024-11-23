package com.example.fashionshopapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CheckoutScreen(
    totalPrice: Double,
    onConfirmPayment: (String) -> Unit,
    onBack: () -> Unit
) {
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
                onClick = { onConfirmPayment("Thanh toán bằng tiền mặt") },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
            ) {
                Text("Tiền mặt khi nhận hàng")
            }

            Button(
                onClick = { onConfirmPayment("Thanh toán bằng thẻ tín dụng") },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
            ) {
                Text("Thẻ tín dụng / Thẻ ghi nợ")
            }

            Button(
                onClick = { onConfirmPayment("Thanh toán qua ví điện tử") },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
            ) {
                Text("Ví điện tử (Momo, ZaloPay)")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth().padding(bottom = 50.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary) ,

            ) {
            Text("Quay lại giỏ hàng")
        }
    }
}
