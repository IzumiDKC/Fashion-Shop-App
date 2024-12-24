import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
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
fun ProductScreen(searchText: String, onAddToCart: (Product) -> Unit, filteredProducts: List<Product>) {
    var products by remember { mutableStateOf<List<Product>>(emptyList()) }
    var brands by remember { mutableStateOf<List<Brand>>(emptyList()) }
    var categories by remember { mutableStateOf<List<Category>>(emptyList()) }
    var successMessage by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(true) } // Trạng thái tải dữ liệu

    val productRepository = ProductRepository()
    val brandRepository = BrandRepository()
    val categoryRepository = CategoryRepository()

    LaunchedEffect(Unit) {
        isLoading = true
        products = productRepository.fetchProducts()
        brands = brandRepository.fetchBrands()
        categories = categoryRepository.fetchCategories()
        isLoading = false
    }

    LaunchedEffect(filteredProducts) {
        products = filteredProducts
    }

    AppBackground {
        Box(modifier = Modifier.fillMaxSize()) {
            if (isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator(color = Color.Gray)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Đang tải sản phẩm, vui lòng chờ trong giây lát", color = Color.Gray)
                    }
                }
            } else {
                Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
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
            }

            if (successMessage != null) {
                LaunchedEffect(successMessage) {
                    kotlinx.coroutines.delay(2000)
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
                            .background(Color(0xFF4CAF50))
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
            Text(text = "Thương hiệu: $brand", color = Color.Black)

            if (product.promotionPrice != null && product.promotionPrice > 0) {
                Text(
                    text = "Giá: ${String.format("%.3f", product.price)} VND",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    textDecoration = TextDecoration.LineThrough // Gạch
                )
                Spacer(modifier = Modifier.height(4.dp))
                // Giá khuyến mãi
                Text(
                    text = buildAnnotatedString {
                        append("Sale: ${String.format("%.3f", product.finalPrice)} VND (")
                        withStyle(style = SpanStyle(color = Color.Red)) {
                            append("KM: ${String.format("%.0f", product.promotionPrice)}%")
                        }
                        append(")")
                    },
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            } else {
                // ko khuyến mãi
                Text(
                    text = "Giá: ${String.format("%.3f", product.price)} VND",
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


