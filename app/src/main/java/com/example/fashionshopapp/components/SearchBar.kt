// SearchBar.kt
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Image

@Composable
fun SearchBar(searchText: String, onSearchTextChange: (String) -> Unit, onImageClick :() -> Unit) {
    Row(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = searchText,
            onValueChange = { onSearchTextChange(it) },
            label = { Text("Tìm kiếm sản phẩm") },
            textStyle = androidx.compose.ui.text.TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                textAlign = TextAlign.Start
            ),
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
        )
        IconButton(onClick = onImageClick) {
            Icon(Icons.Default.Image, contentDescription = "Mở kho lưu hình ảnh")
        }
    }
}
