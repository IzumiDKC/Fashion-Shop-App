import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fashionshopapp.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    private val repository = AuthRepository()
    val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn

    var username by mutableStateOf<String?>(null)
    var errorMessage by mutableStateOf("")

    fun login(username: String, password: String, onLoginResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            repository.login(username, password) { success, token ->
                if (success) {
                    this@ProfileViewModel.username = username
                    _isLoggedIn.value = true
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
}
