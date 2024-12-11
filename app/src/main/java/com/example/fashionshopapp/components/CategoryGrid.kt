import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fashionshopapp.R
import com.example.fashionshopapp.models.CustomCategory

@Composable
fun CategoryGrid(navController: NavController) {
    val categories = listOf(
        CustomCategory(1, "Khuyến mãi", R.drawable.sale, "Các sản phẩm được giảm giá"),
        CustomCategory(2, "Sản phẩm hot", R.drawable.hot, "Những sản phẩm đang được yêu thích"),
        CustomCategory(3, "Flash Sale", R.drawable.sale, "Các sản phẩm chỉ giảm giá trong thời gian giới hạn, đừng bỏ lỡ!"),
        CustomCategory(4, "Phụ kiện độc đáo", R.drawable.sale, "Khám phá những phụ kiện tinh tế để làm nổi bật phong cách của bạn."),
        CustomCategory(5, "BST Giáng Sinh", R.drawable.noel, "Những sản phẩm độc quyền, mang đậm không khí lễ hội."),
        CustomCategory(6, "Cosplay", R.drawable.sale, "Khám phá các bộ trang phục cosplay độc đáo, tái hiện nhân vật yêu thích.")
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        categories.chunked(3).forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                rowItems.forEach { category ->
                    CustomCategoryItemView(
                        category = category,
                        modifier = Modifier
                            .weight(1f, fill = true)
                            .padding(4.dp),
                        navController = navController
                    )
                }
            }
        }
    }
}


@Composable
fun CustomCategoryItemView(category: CustomCategory, modifier: Modifier = Modifier, navController: NavController) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = category.icon),
            contentDescription = category.name,
            modifier = Modifier.size(30.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = category.name,
            fontSize = 14.sp,
            color = Color.Black,
            modifier = Modifier.clickable {
                if (category.name == "Khuyến mãi") {
                    // Điều hướng đến SaleScreen khi nhấn vào "Khuyến mãi"
                    navController.navigate("sale_screen/${category.description}")
                }
            }
        )
    }
}


