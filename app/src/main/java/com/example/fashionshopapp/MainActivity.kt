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
import ProfileViewModel
import android.annotation.SuppressLint
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.fashionshopapp.screens.CartScreen
import com.example.fashionshopapp.screens.CheckoutScreen
import com.example.fashionshopapp.screens.LoginScreen
import com.example.fashionshopapp.viewmodel.CartViewModel
import com.example.fashionshopapp.screens.ProfileScreen
import com.example.fashionshopapp.screens.RegisterScreen
import com.example.fashionshopapp.screens.WeatherScreen

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
        Screen.Weather,
        Screen.Cart,
        Screen.Profile
    )

    BottomNavigation(
        backgroundColor = Color(0xFFFDEBEC),
        contentColor = Color(0xFFFF7973)
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
                unselectedContentColor = Color(0xFFFF7973),
                selectedContentColor = Color(0xFFFF7973)
            )
        }
    }
}



sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Home : Screen("home", "Trang Chủ", Icons.Default.Home)
    object Weather : Screen("weather", "Weather", Icons.Default.Star)
    object Cart : Screen("cart", "Giỏ Hàng", Icons.Default.ShoppingCart)
    object Profile : Screen("profile", "Hồ Sơ", Icons.Default.Person)
}

@Composable
fun NavigationGraph(navController: NavHostController) {
    val profileViewModel: ProfileViewModel = viewModel()
    val cartViewModel = viewModel<CartViewModel>()

    NavHost(navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) { HomeScreen(cartViewModel) }
        composable(Screen.Weather.route) { WeatherScreen() }

        composable(Screen.Cart.route) { CartScreen( navController, cartViewModel, profileViewModel = profileViewModel) }

        composable(Screen.Profile.route) {
            ProfileScreen(viewModel = profileViewModel, navController = navController)
        }
        composable("login") {
            LoginScreen(viewModel = profileViewModel, navController = navController)
        }
        composable("register") {
            RegisterScreen(viewModel = profileViewModel, navController = navController)
        }

        composable(
            route = "checkout/{totalPrice}",
            arguments = listOf(navArgument("totalPrice") { type = NavType.StringType })
        ) { backStackEntry ->
            val totalPrice = backStackEntry.arguments?.getString("totalPrice")?.toDoubleOrNull() ?: 0.0
            CheckoutScreen(
                totalPrice = totalPrice,
                onConfirmPayment = { paymentMethod ->
                    println("Phương thức thanh toán: $paymentMethod")
                    navController.popBackStack(Screen.Home.route, false) // Điều hướng về Trang Chủ
                },
                onBack = { navController.popBackStack() }
            )
        }

    }
}




@Composable
fun HomeScreen(cartViewModel: CartViewModel = viewModel()) {

    AppBackground {
        Column(modifier = Modifier.fillMaxSize()) {
            BannerCarousel()
            CategoryGrid()
            Spacer(modifier = Modifier.height(16.dp))
            ProductScreen(onAddToCart = { product -> cartViewModel.addToCart(product) })

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



