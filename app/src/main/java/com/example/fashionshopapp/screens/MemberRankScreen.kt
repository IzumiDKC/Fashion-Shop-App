package com.example.fashionshopapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fashionshopapp.R
import com.example.fashionshopapp.viewmodel.ProfileViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fashionshopapp.viewmodel.HistoryOrderViewModel

@Composable
fun MemberRankScreen(
    profileViewModel: ProfileViewModel,
    historyOrderViewModel: HistoryOrderViewModel = viewModel(),
    onBackClick: () -> Unit
) {
    val userId = profileViewModel.userId
    val orders = historyOrderViewModel.orders.collectAsState(initial = null).value
    val isLoading = historyOrderViewModel.isLoading.collectAsState(initial = false).value
    val errorMessage = historyOrderViewModel.errorMessage.collectAsState(initial = null).value

    val totalOrderPrice = orders?.sumOf { it.totalPrice.toLong() } ?: 0L

    fun determineRank(totalPrice: Long): Pair<String, Int> {
        return when (totalPrice) {
            in 0..500 -> "Đồng" to R.drawable.brozon
            in 501..1000 -> "Bạc" to R.drawable.silver
            in 1001..2000 -> "Vàng" to R.drawable.gold
            in 2001..3500 -> "Bạch kim" to R.drawable.platinum
            in 3501..5000 -> "Kim Cương" to R.drawable.diamond
            else -> "V. I . P" to R.drawable.legend
        }
    }

    val (memberRank, rankIcon) = determineRank(totalOrderPrice)

    LaunchedEffect(userId) {
        if (!userId.isNullOrEmpty()) {
            historyOrderViewModel.fetchOrders(userId)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Xếp Hạng Thành Viên", color = Color.White) },
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
                    else -> {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Image(
                                painter = painterResource(id = rankIcon),
                                contentDescription = "Rank Icon",
                                modifier = Modifier
                                    .size(120.dp)
                                    .padding(8.dp)
                                    .align(Alignment.CenterHorizontally)
                            )

                            Text(
                                text = "Tổng giá trị đơn hàng: ${String.format("%,d", totalOrderPrice)} VND",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = Color(0xFF007BFF),
                                modifier = Modifier.padding(top = 8.dp)
                            )

                            Text(
                                text = "Hạng Thành Viên: $memberRank",
                                fontWeight = FontWeight.Bold,
                                fontSize = 24.sp,
                                color = when (memberRank) {
                                    "Đồng" -> Color.Gray
                                    "Bạc" -> Color(0xFFC0C0C0)
                                    "Vàng" -> Color(0xFFFFD700)
                                    "Bạch kim" -> Color(0xFFE5E4E2)
                                    "Kim Cương" -> Color(0xFF00FFFF)
                                    else -> Color(0xFF8A2BE2)
                                },
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }
            }
        }
    )
}
