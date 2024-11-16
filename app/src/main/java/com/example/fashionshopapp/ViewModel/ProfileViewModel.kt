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

    var isLoggedIn by mutableStateOf(false)
        private set
    var errorMessage by mutableStateOf("") // Để lưu noti lỗi

    fun login(username: String, password: String, onLoginResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            repository.login(username, password) { success, token ->
                if (success) {
                    isLoggedIn = true
                    // Lưu token vào SharedPreferences
                }
                onLoginResult(success)
            }
        }
    }

    fun logout() {
        isLoggedIn = false
        // Xóa token hoặc các thông tin đăng nhập lưu trữ (nếu cần)
    }
    fun register(username: String, password: String, fullName: String, email: String, onRegisterResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            repository.register(username, password, fullName, email) { success ->
                onRegisterResult(success)
            }
        }
    }

}
