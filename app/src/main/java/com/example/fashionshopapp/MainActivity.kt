package com.example.fashionshopapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fashionshopapp.ui.theme.FashionShopAppTheme
import com.example.fashionshopapp.utils.AppBackground
import com.example.fashionshopapp.utils.BannerCarousel
import CategoryGrid
import ProductScreen
import android.annotation.SuppressLint
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.fashionshopapp.screens.ProfileScreen
import com.example.fashionshopapp.viewmodel.ProfileViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FashionShopAppTheme {
                MainScreen()
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) {
        NavigationGraph(navController)
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        Screen.Home,
        Screen.Test1,
        Screen.Test2,
        Screen.Profile
    )

    BottomNavigation(
        backgroundColor = Color(0xFFFDEBEC),  // Màu nền của thanh công cụ dưới
        contentColor = Color(0xFFFF7973)  // Màu chữ và icon khi mục được chọn
    ) {
        items.forEach { screen ->
            BottomNavigationItem(
                icon = { Icon(screen.icon, contentDescription = null) },
                label = { Text(screen.title) },
                selected = false,  // Cập nhật nếu có trạng thái selected
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                unselectedContentColor = Color(0xFFFF7973),  // Màu chữ khi chưa được chọn
                selectedContentColor = Color(0xFFFF7973)  // Màu chữ khi được chọn
            )
        }
    }
}



sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Home : Screen("home", "Trang Chủ", Icons.Default.Home)
    object Test1 : Screen("test1", "Test 1", Icons.Default.Star)
    object Test2 : Screen("test2", "Test 2", Icons.Default.Favorite)
    object Profile : Screen("profile", "Hồ Sơ", Icons.Default.Person)
}

@Composable
fun NavigationGraph(navController: NavHostController) {
    val isLoggedIn = remember { mutableStateOf(false) } // Quản lý trạng thái đăng nhập
    val profileViewModel = ProfileViewModel() // Khởi tạo ViewModel

    NavHost(navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) { HomeScreen() }
        composable(Screen.Test1.route) { Test1Screen() }
        composable(Screen.Test2.route) { Test2Screen() }
        composable(Screen.Profile.route) {
            ProfileScreen(viewModel = profileViewModel)  // Chỉ gọi ProfileScreen một lần
        }
    }
}



@Composable
fun HomeScreen() {
    AppBackground {
        Column(modifier = Modifier.fillMaxSize()) {
            BannerCarousel()
            CategoryGrid()
            Spacer(modifier = Modifier.height(16.dp))
            ProductScreen()
        }
    }
}

@Composable
fun Test1Screen() {
    AppBackground {
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
            Text("Đây là màn hình Test 1")
        }
    }
}

@Composable
fun Test2Screen() {
    AppBackground {
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
            Text("Đây là màn hình Test 2")
        }
    }
}

@Composable
fun ProfileScreen() {
    val viewModel = ProfileViewModel()
    ProfileScreen(viewModel = viewModel)
}
