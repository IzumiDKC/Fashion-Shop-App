import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.remember
import com.example.fashionshopapp.models.Order
import com.example.fashionshopapp.viewmodel.HistoryOrderViewModel
import com.example.fashionshopapp.viewmodel.ProfileViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun HistoryOrderScreen(
    profileViewModel: ProfileViewModel,
    historyOrderViewModel: HistoryOrderViewModel = viewModel(),
    onBackClick: () -> Unit
) {
    val userId = profileViewModel.userId
    val orders = historyOrderViewModel.orders.collectAsState(initial = null).value
    val isLoading = historyOrderViewModel.isLoading.collectAsState(initial = false).value
    val errorMessage = historyOrderViewModel.errorMessage.collectAsState(initial = null).value

    LaunchedEffect(userId) {
        if (!userId.isNullOrEmpty()) {
            historyOrderViewModel.fetchOrders(userId)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Lịch sử đơn hàng", color = Color.White) },
                backgroundColor = Color(0xFF007BFF),
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                }
            )
        },
        content = { padding ->
            Box(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
            ) {
                when {
                    isLoading -> {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center),
                            color = Color(0xFF007BFF)
                        )
                    }
                    !errorMessage.isNullOrEmpty() -> {
                        Text(
                            text = "Lỗi: $errorMessage",
                            color = Color.Red,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                    orders.isNullOrEmpty() -> {
                        Text(
                            text = "Không có đơn hàng nào.",
                            color = Color.Gray,
                            fontSize = 16.sp,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                    else -> {
                        Column(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            /*Text(
                                text = "Tổng giá trị đơn hàng: ${String.format("%,.0f", totalOrderPrice.toDouble())} VND",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = Color(0xFF007BFF),
                                modifier = Modifier
                                    .padding(16.dp)
                                    .align(Alignment.CenterHorizontally)
                            )*/
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = 8.dp),
                                contentPadding = PaddingValues(bottom = 16.dp)
                            ) {
                                items(orders) { order ->
                                    OrderItem(order)
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}


@Composable
fun OrderItem(order: Order) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 6.dp,
        backgroundColor = Color(0xFFF5F5F5)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Mã đơn: #${order.id}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Black
                )
                val formattedDate = try {
                    val originalFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS")
                    val displayFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
                    LocalDateTime.parse(order.orderDate, originalFormat).format(displayFormat)
                } catch (e: Exception) {
                    "Không xác định"
                }
                Text(
                    text = formattedDate,
                    fontSize = 14.sp,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Tổng tiền: ${String.format("%,.3f", order.totalPrice)} VND",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = Color(0xFF007BFF)
                )
                Text(
                    text = "${order.status}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = when (order.status) {
                        "Đã giao" -> Color(0xFF4CAF50)
                        "Đang xử lý" -> Color(0xFF4CAF50)
                        else -> Color.Red
                    }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Địa chỉ: ${order.shippingAddress}",
                fontSize = 14.sp,
                color = Color.Black
            )
            Text(
                text = "Ghi chú: ${order.notes ?: "Không có"}",
                fontSize = 14.sp,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            Divider(
                color = Color.LightGray,
                thickness = 1.dp,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Text(
                text = "Chi tiết sản phẩm:",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color.Black
            )

            order.orderDetails.forEachIndexed { index, detail ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "${index + 1}. ${detail.name} x ${detail.quantity}",
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp,
                            color = Color.Black
                        )
                    }
                    Text(
                        text = String.format("%,.3f VND", detail.finalPrice),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF007BFF),
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
            }
        }
    }
}

