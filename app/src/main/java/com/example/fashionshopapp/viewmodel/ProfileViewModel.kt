package com.example.fashionshopapp.viewmodel

import android.content.Context
import android.content.SharedPreferences
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

class ProfileViewModel(private val context: Context) : ViewModel() {
    private val repository = AuthRepository()

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("FashionShopPrefs", Context.MODE_PRIVATE)

    val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn
    var username by mutableStateOf<String?>(null)
    var userId by mutableStateOf<String?>(null)
    var token by mutableStateOf<String?>(null)

    fun getProfile(onResult: (UserProfile?) -> Unit) {
        val userId = this.userId
        if (userId != null) {
            viewModelScope.launch {
                try {
                    val response = RetrofitInstance.api.getProfile(userId)
                    if (response.isSuccessful) {
                        val profileDetail = response.body()
                        onResult(profileDetail)
                    } else {
                        onResult(null)
                    }
                } catch (e: Exception) {
                    onResult(null)
                }
            }
        } else {
            onResult(null)
        }
    }

    fun updateProfile(userId: String, updatedProfile: UpdatedProfileModel, onUpdateResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.updateProfile(userId, updatedProfile)
                if (response.isSuccessful) {
                    onUpdateResult(true)
                } else {
                    onUpdateResult(false)
                }
            } catch (e: Exception) {
                onUpdateResult(false)
            }
        }
    }

    init {
        token = sharedPreferences.getString("auth_token", null)
        userId = sharedPreferences.getString("user_id", null)
        _isLoggedIn.value = !token.isNullOrEmpty()
        Log.d("ProfileViewModel", "Token: $token, UserId: $userId")
    }

    fun login(username: String, password: String, onLoginResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            repository.login(username, password) { success, token, userId ->
                if (success) {
                    _isLoggedIn.value = true
                    this@ProfileViewModel.token = token
                    this@ProfileViewModel.username = username
                    this@ProfileViewModel.userId = userId
                    Log.d("Login", userId ?: "No UserId")

                    sharedPreferences.edit().apply(){
                        putString("token", token)
                        putString("user_id", userId)
                        apply()
                    }

                    RetrofitInstance.token = token

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
        token = null

        clearAuthToken()
    }

    fun clearAuthToken(){
        sharedPreferences.edit().apply {
            remove("auth_token")
            remove("user_id")
            apply()
        }
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
