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
import com.example.fashionshopapp.viewmodel.ProfileViewModel
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.rounded.WbSunny
import android.Manifest
import android.os.Looper
import androidx.compose.ui.Alignment

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.fashionshopapp.screens.CartScreen
import com.example.fashionshopapp.screens.CheckoutScreen
import com.example.fashionshopapp.screens.HistoryOrderScreen
import com.example.fashionshopapp.screens.HotScreen
import com.example.fashionshopapp.screens.LoginScreen
import com.example.fashionshopapp.screens.ProfileDetail
import com.example.fashionshopapp.viewmodel.CartViewModel
import com.example.fashionshopapp.screens.ProfileScreen
import com.example.fashionshopapp.screens.RegisterScreen
import com.example.fashionshopapp.screens.SaleScreen
import com.example.fashionshopapp.screens.UpdateProfile
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
                selected = false,
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
    object Weather : Screen("weather", "Gợi Ý", Icons.Rounded.WbSunny)
    object Cart : Screen("cart", "Giỏ Hàng", Icons.Default.ShoppingCart)
    object Profile : Screen("profile", "Hồ Sơ", Icons.Default.Person)

}

@Composable
fun NavigationGraph(navController: NavHostController) {
    val profileViewModel: ProfileViewModel = viewModel()
    val cartViewModel = viewModel<CartViewModel>()

    NavHost(navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) { HomeScreen(cartViewModel, navController) }

        composable(Screen.Weather.route) { WeatherScreen() }

        composable(Screen.Cart.route) { CartScreen(navController, cartViewModel, profileViewModel = profileViewModel) }

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
                    navController.popBackStack(Screen.Home.route, false)
                },
                onBack = { navController.popBackStack() }
            )
        }
        composable("history_order") {
            HistoryOrderScreen()
        }
        composable("profile_detail") {
            ProfileDetail(viewModel = profileViewModel, navController = navController)
        }
        composable("updateProfile/{userId}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            if (userId.isNotEmpty()) {
                UpdateProfile(viewModel = profileViewModel, navController, userId)
            } else {
                navController.popBackStack(Screen.Home.route, false)
            }
        }
        composable(
            "sale_screen/{description}",
            arguments = listOf(navArgument("description") { type = NavType.StringType })
        ) { backStackEntry ->
            val description = backStackEntry.arguments?.getString("description") ?: ""
            SaleScreen(
                onBack = { navController.popBackStack() },
                onAddToCart = { product ->
                    cartViewModel.addToCart(product)
                },
                description = description
            )
        }
        composable(
            "hot_screen/{description}",
            arguments = listOf(navArgument("description") { type = NavType.StringType })
        ) { backStackEntry ->
            val description = backStackEntry.arguments?.getString("description") ?: ""
            HotScreen(
                onBack = { navController.popBackStack() },
                onAddToCart = { product ->
                    cartViewModel.addToCart(product)
                },
                description = description
            )
        }


    }
}

@Composable
fun HomeScreen(cartViewModel: CartViewModel = viewModel(), navController: NavHostController) {
    var searchText by remember { mutableStateOf("") }

    AppBackground {
        Column(modifier = Modifier.fillMaxSize()) {
            BannerCarousel()
            CategoryGrid(navController = navController)
            SearchBar(searchText = searchText, onSearchTextChange = { searchText = it })
            ProductScreen(searchText = searchText, onAddToCart = { product -> cartViewModel.addToCart(product) })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(searchText: String, onSearchTextChange: (String) -> Unit) {
    val context = LocalContext.current
    var isListening by remember { mutableStateOf(false) }
    var recognitionResult by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }

    if (isListening) {
        Dialog(onDismissRequest = { isListening = false }) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xAA000000)),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.Mic,
                        contentDescription = "Listening",
                        tint = Color.White,
                        modifier = Modifier.size(100.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "Đang nghe...", color = Color.White, fontSize = 18.sp)
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = {
                        isListening = false
                        recognitionResult = ""
                    }) {
                        Text("Hủy")
                    }
                }
            }
        }
    }

    if (showError) {
        Toast.makeText(context, "Không tìm thấy âm thanh", Toast.LENGTH_SHORT).show()
        showError = false
    }

    TextField(
        value = if (recognitionResult.isEmpty()) searchText else recognitionResult,
        onValueChange = { onSearchTextChange(it) },
        label = { Text("Tìm tên quần áo") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = Color.Gray
            )
        },
        trailingIcon = {
            IconButton(onClick = {
                requestMicrophonePermission(context) { isGranted ->
                    if (isGranted) {
                        recognitionResult = ""
                        isListening = true
                        startSpeechRecognition(context, onResult = { result ->
                            isListening = false
                            recognitionResult = result
                            onSearchTextChange(result)
                        }, onTimeout = {
                            isListening = false
                            showError = true
                        })
                    }
                }
            }) {
                Icon(
                    imageVector = Icons.Default.Mic,
                    contentDescription = "Microphone",
                    tint = Color.Gray
                )
            }
        },
        textStyle = androidx.compose.ui.text.TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            textAlign = TextAlign.Start
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(Color(0xFFF1F1F1), shape = MaterialTheme.shapes.medium)
            .height(56.dp)
            .padding(start = 16.dp, end = 16.dp),
        singleLine = true,
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color(0xFFF1F1F1),
            focusedLabelColor = Color.Black,
            unfocusedLabelColor = Color.Gray,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}
fun requestMicrophonePermission(context: Context, callback: (Boolean) -> Unit) {
    if (ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(
            context as Activity,
            arrayOf(Manifest.permission.RECORD_AUDIO),
            1
        )
        callback(false)
    } else {
        callback(true)
    }
}

fun startSpeechRecognition(
    context: Context,
    onResult: (String) -> Unit,
    onTimeout: () -> Unit
) {
    val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
        putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        putExtra(RecognizerIntent.EXTRA_LANGUAGE, "vi-VN")
    }

    val recognizer = SpeechRecognizer.createSpeechRecognizer(context)
    val handler = android.os.Handler(Looper.getMainLooper())
    var isResultReceived = false

    recognizer.setRecognitionListener(object : RecognitionListener {
        override fun onReadyForSpeech(params: Bundle?) {}

        override fun onBeginningOfSpeech() {}

        override fun onRmsChanged(rmsdB: Float) {}

        override fun onBufferReceived(buffer: ByteArray?) {}

        override fun onEndOfSpeech() {}

        override fun onError(error: Int) {
            if (!isResultReceived) {
                onTimeout()
            }
        }

        override fun onResults(results: Bundle?) {
            isResultReceived = true
            handler.removeCallbacksAndMessages(null)
            val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
            if (!matches.isNullOrEmpty()) {
                onResult(matches[0])
            } else {
                onTimeout()
            }
        }

        override fun onPartialResults(partialResults: Bundle?) {}

        override fun onEvent(eventType: Int, params: Bundle?) {}
    })

    handler.postDelayed({
        if (!isResultReceived) {
            recognizer.stopListening()
            onTimeout()
        }
    }, 5000)

    recognizer.startListening(intent)
}





