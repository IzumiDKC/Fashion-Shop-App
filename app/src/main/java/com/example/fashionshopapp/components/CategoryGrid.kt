import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fashionshopapp.R
import com.example.fashionshopapp.models.CustomCategory

@Composable
fun CategoryGrid() {
    val categories = listOf(
        CustomCategory(1, "Khuyến mãi", R.drawable.sale),
        CustomCategory(2, "Sản phẩm hot", R.drawable.sale),
        CustomCategory(3, "Giảm giá", R.drawable.sale),
        CustomCategory(4, "Test 4", R.drawable.sale),
        CustomCategory(5, "Test 5", R.drawable.sale),
        CustomCategory(6, "Test 6", R.drawable.sale),
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
                            .padding(4.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun CustomCategoryItemView(category: CustomCategory, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = category.iconUrl),
            contentDescription = category.name,
            modifier = Modifier.size(30.dp) // Tăng kích thước ảnh cho rõ hơn
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = category.name,
            fontSize = 14.sp,
            color = Color.Black
        )
    }
}

