package com.example.fashionshopapp.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fashionshopapp.repository.AuthRepository
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    private val repository = AuthRepository()

    var isLoggedIn by mutableStateOf(false)
        private set

    fun login(username: String, password: String, onLoginResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            repository.login(username, password) { success, token ->
                if (success) {
                    isLoggedIn = true
                    // Lưu token nếu cần thiết vào SharedPreferences hoặc một nơi an toàn khác
                }
                onLoginResult(success)
            }
        }
    }

    fun logout() {
        isLoggedIn = false
        // Xóa token hoặc các thông tin đăng nhập lưu trữ (nếu cần)
    }
}
