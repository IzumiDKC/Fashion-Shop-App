package com.example.fashionshopapp.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fashionshopapp.api.RetrofitInstance
import com.example.fashionshopapp.models.UpdatedProfileModel
import com.example.fashionshopapp.models.UserProfile
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
    var userProfile by mutableStateOf<UserProfile?>(null)
    var userId by mutableStateOf<String?>(null) // Lưu userId


    fun getProfile(onResult: (UserProfile?) -> Unit) {
        val userId = this.userId
        if (userId != null) {
            viewModelScope.launch {
                // Gọi API GetProfile với userId
                try {
                    val response = RetrofitInstance.api.getProfile(userId) // Gọi API ở đây
                    if (response.isSuccessful) {
                        val profileDetail = response.body() // Lấy dữ liệu profile
                        onResult(profileDetail) // Trả về dữ liệu
                    } else {
                        onResult(null) // Không thành công
                    }
                } catch (e: Exception) {
                    onResult(null) // Xử lý lỗi
                }
            }
        } else {
            onResult(null) // Nếu không có userId
        }
    }

    fun updateProfile(userId: String, updatedProfile: UpdatedProfileModel, onUpdateResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.updateProfile(userId, updatedProfile)
                if (response.isSuccessful) {
                    onUpdateResult(true) // Cập nhật thành công
                } else {
                    onUpdateResult(false) // Cập nhật thất bại
                }
            } catch (e: Exception) {
                onUpdateResult(false) // Xử lý lỗi
            }
        }
    }




    fun login(username: String, password: String, onLoginResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            repository.login(username, password) { success, token, userId ->
                if (success) {
                    _isLoggedIn.value = true
                    this@ProfileViewModel.username = username
                    this@ProfileViewModel.userId = userId // Lưu userId sau khi login
                    Log.d("Login", userId ?: "No UserId")
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
        onRegisterResult: (Boolean, List<String>?, Boolean) -> Unit
    ) {
        viewModelScope.launch {
            repository.register(username, password, fullName, email) { success, errors ->
                if (success) {
                    // Tự động đăng nhập sau khi đăng ký
                    login(username, password) { loginSuccess ->
                        onRegisterResult(success, errors, loginSuccess)
                            Log.d("AuthToken", "Token: ${repository.getCurrentToken()}")
                    }
                } else {
                    onRegisterResult(false, errors, false)
                }
            }
        }
    }




}
