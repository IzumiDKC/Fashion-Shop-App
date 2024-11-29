package com.example.fashionshopapp.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fashionshopapp.api.RegisterRequest
import com.example.fashionshopapp.repository.AuthRepository
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    private val repository = AuthRepository()

    // Các trạng thái của người dùng
    var isLoggedIn by mutableStateOf(false)
        private set
    var username by mutableStateOf<String?>(null) // Lưu trữ tên người dùng
    var errorMessage by mutableStateOf("") // Lưu thông báo lỗi khi có lỗi

    // Đăng nhập
    fun login(username: String, password: String, onLoginResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            repository.login(username, password) { success, token ->
                if (success) {
                    this@ProfileViewModel.username = username  // Lưu tên người dùng khi đăng nhập thành công
                    isLoggedIn = true
                    // Lưu token vào SharedPreferences (nếu cần)
                }
                onLoginResult(success)
            }
        }
    }

    // Đăng xuất
    fun logout() {
        isLoggedIn = false
        username = null  // Xóa thông tin người dùng khi đăng xuất
        // Xóa token hoặc các thông tin đăng nhập lưu trữ (nếu cần)
    }

    // Đăng ký người dùng mới
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
                    this@ProfileViewModel.username = username  // Lưu tên người dùng sau khi đăng ký thành công
                }
                onRegisterResult(success, errors)
            }
        }
    }
}
