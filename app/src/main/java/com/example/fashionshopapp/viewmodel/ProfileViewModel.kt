import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fashionshopapp.repository.AuthRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    private val repository = AuthRepository()
    val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn

    var username by mutableStateOf<String?>(null)
    var errorMessage by mutableStateOf("")
    var isRefreshing = MutableStateFlow(false) // Trạng thái làm mới

    fun login(username: String, password: String, onLoginResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            repository.login(username, password) { success, token ->
                if (success) {
                    _isLoggedIn.value = true
                    this@ProfileViewModel.username = username
                } else {
                    _isLoggedIn.value = false
                }
                onLoginResult(success)
            }
        }
    }

    fun logout() {
        _isLoggedIn.value = false
        username = null
        // Xóa token
    }

    fun register(
        username: String,
        fullName: String,
        email: String,
        password: String,
        onRegisterResult: (Boolean, List<String>?) -> Unit
    ) {
        viewModelScope.launch {
            repository.register(username, password, fullName, email) { success, errors ->
                if (success) {
                    this@ProfileViewModel.username = username
                }
                onRegisterResult(success, errors)
            }
        }
    }

    // Làm mới dữ liệu
    fun refreshProfileData() {
        viewModelScope.launch {
            isRefreshing.value = true // Bắt đầu làm mới
            delay(2000) // Mô phỏng thời gian lấy dữ liệu
            // TODO: Gọi API để làm mới dữ liệu
            isRefreshing.value = false // Hoàn tất làm mới
        }
    }
}
