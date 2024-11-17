import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.fashionshopapp.R
import com.example.fashionshopapp.Repository.BrandRepository
import com.example.fashionshopapp.Repository.CategoryRepository
import com.example.fashionshopapp.Repository.ProductRepository
import com.example.fashionshopapp.models.Brand
import com.example.fashionshopapp.models.Category
import com.example.fashionshopapp.models.Product
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.pager.HorizontalPager
import com.example.fashionshopapp.utils.AppBackground
import com.example.fashionshopapp.viewmodel.CartViewModel

@Composable
fun ProductScreen(cartViewModel: CartViewModel) {
    var products by remember { mutableStateOf<List<Product>>(emptyList()) }
    var brands by remember { mutableStateOf<List<Brand>>(emptyList()) }
    var categories by remember { mutableStateOf<List<Category>>(emptyList()) }
    var searchText by remember { mutableStateOf("") }

    val productRepository = ProductRepository()
    val brandRepository = BrandRepository()
    val categoryRepository = CategoryRepository()

    LaunchedEffect(Unit) {
        products = productRepository.fetchProducts()
        brands = brandRepository.fetchBrands()
        categories = categoryRepository.fetchCategories()
    }

    AppBackground {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {


            // Thanh tìm kiếm
            SearchBar(searchText = searchText, onSearchTextChange = { searchText = it })

            val filteredProducts = products.filter {
                it.name.contains(searchText, ignoreCase = true)
            }

            // Danh sách sản phẩm
            if (filteredProducts.isEmpty()) {
                Text(text = "Không có sản phẩm nào.", color = Color.Gray)
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(filteredProducts) { product ->
                        ProductItem(product, brands, categories, cartViewModel)
                    }
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
    cartViewModel: CartViewModel
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

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = product.name,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = "Giá: ${product.price}00 VND",
                color = Color.Black
            )
            product.promotionPrice?.takeIf { it > 0 }?.let { promotionPrice ->
                Text(text = "Khuyến mãi: $promotionPrice%", color = Color.Red)
            }
            Text(text = "Hãng: $brand", color = Color.Black)
            Text(text = "Loại: $category", color = Color.Black)

            Row {
                Button(
                    onClick = { cartViewModel.addProductToCart(product) },
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Text(text = "Thêm vào giỏ")
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = { /* Xử lý mua ngay sau */ },
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Text(text = "Mua ngay")
                }
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


