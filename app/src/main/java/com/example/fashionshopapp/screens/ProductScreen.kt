import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.fashionshopapp.R
import com.example.fashionshopapp.models.Product
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.pager.HorizontalPager
import com.example.fashionshopapp.models.Brand
import com.example.fashionshopapp.models.Category
import com.example.fashionshopapp.repository.BrandRepository
import com.example.fashionshopapp.repository.CategoryRepository
import com.example.fashionshopapp.utils.AppBackground

@Composable
fun ProductScreen(onAddToCart: (Product) -> Unit) {
    var products by remember { mutableStateOf<List<Product>>(emptyList()) }
    var brands by remember { mutableStateOf<List<Brand>>(emptyList()) }
    var categories by remember { mutableStateOf<List<Category>>(emptyList()) }
    var searchText by remember { mutableStateOf("") }
    var successMessage by remember { mutableStateOf<String?>(null) }

    val productRepository = ProductRepository()
    val brandRepository = BrandRepository()
    val categoryRepository = CategoryRepository()

    LaunchedEffect(Unit) {
        products = productRepository.fetchProducts()
        brands = brandRepository.fetchBrands()
        categories = categoryRepository.fetchCategories()
    }

    AppBackground {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                SearchBar(searchText = searchText, onSearchTextChange = { searchText = it })

                val filteredProducts = products.filter {
                    it.name.contains(searchText, ignoreCase = true)
                }

                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(filteredProducts) { product ->
                        ProductItem(
                            product = product,
                            brands = brands,
                            categories = categories,
                            onAddToCart = {
                                onAddToCart(product)
                                successMessage = "Đã thêm ${product.name} vào giỏ hàng!"
                            }
                        )
                    }
                }
            }

            if (successMessage != null) {
                LaunchedEffect(successMessage) {
                    kotlinx.coroutines.delay(2000) // Tự động ẩn thông báo sau 2 giây
                    successMessage = null
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Text(
                        text = successMessage ?: "",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .background(Color(0xFF4CAF50)) // Màu nền thông báo
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }
            }
        }
    }
}








@Composable
fun ProductItem(
    product: Product,
    brands: List<Brand>,
    categories: List<Category>,
    onAddToCart: (Product) -> Unit
) {
    val brand = brands.find { it.id == product.brandId }?.name ?: "Unknown Brand"
    val category = categories.find { it.id == product.categoryId }?.name ?: "Unknown Category"

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberImagePainter(product.imageUrl),
            contentDescription = null,
            modifier = Modifier.size(100.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(text = product.name, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black)
         //   Text(text = "Danh mục: $category", color = Color.Black)
            Text(text = "Thương hiệu: $brand", color = Color.Black)
            if (product.promotionPrice != null && product.promotionPrice > 0) {
                Text(
                    text = "Giá: ${product.price}00 VND",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    textDecoration = TextDecoration.LineThrough // Gạch ngang
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Hiển thị giá khuyến mãi và phần khuyến mãi màu đỏ
                Text(
                    text = buildAnnotatedString {
                        append("Giá: ${product.finalPrice}00 VND (")
                        withStyle(style = SpanStyle(color = Color.Red)) {
                            append("KM: ${product.promotionPrice}%")
                        }
                        append(")")
                    },
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            } else {
                Text(
                    text = "Giá: ${product.price}00 VND",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            }


            Button(onClick = {
                onAddToCart(product)
            }) {
                Text(text = "Thêm vào giỏ")
            }
        }
    }
}




@OptIn(ExperimentalPagerApi::class)
@Composable
fun BannerCarousel() {
    val images = listOf(
        R.drawable.banner1,
        R.drawable.banner2,
        R.drawable.banner3
    )
    val pagerState = rememberPagerState(initialPage = 0)
    val coroutineScope = rememberCoroutineScope()

    // Tự động cuộn
    LaunchedEffect(Unit) {
        while (true) {
            delay(3000) // Chuyển hình mỗi 3 giây
            coroutineScope.launch {
                pagerState.animateScrollToPage((pagerState.currentPage + 1) % images.size)
            }
        }
    }

    HorizontalPager(
        state = pagerState,
        count = images.size,
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) { page ->
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = rememberImagePainter(images[page]),
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}


