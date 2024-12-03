package com.example.fashionshopapp.repository

import android.util.Log
import com.example.fashionshopapp.api.LoginRequest
import com.example.fashionshopapp.api.LoginResponse
import com.example.fashionshopapp.api.RegisterRequest
import com.example.fashionshopapp.api.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthRepository {

    private var currentToken: String? = null

    fun getCurrentToken(): String? = currentToken


    fun login(username: String, password: String, onResult: (Boolean, String?, String?) -> Unit) {
        val request = LoginRequest(username, password)
        RetrofitInstance.api.login(request).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val token = response.body()?.token
                    val userId = response.body()?.userId  // Lấy userId
                    currentToken = token // Lưu token
                    Log.d("LoginToken", "Token: $token, UserId: $userId")

                    onResult(true, token, userId)
                } else {
                    onResult(false, null, null)
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                onResult(false, null, null)
            }
        })
    }
    fun register(
        username: String,
        password: String,
        fullName: String,
        email: String,
        callback: (Boolean, List<String>?) -> Unit
    ) {
        val request = RegisterRequest(username = username, email = email, password = password, fullName = fullName)
        Log.d("RegisterDebug", "Request data: $request")

        RetrofitInstance.api.register(request).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    callback(true, null)
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("RegisterError", "Error: $errorBody")

                    val errorMessages = mutableListOf<String>()
                    errorBody?.let {
                        try {
                            val errors = JSONArray(it)
                            for (i in 0 until errors.length()) {
                                val error = errors.getJSONObject(i)
                                errorMessages.add(error.getString("description"))
                            }
                        } catch (e: Exception) {
                            errorMessages.add("Đăng ký thất bại, vui lòng thử lại sau.")
                            Log.e("RegisterParseError", "Error parsing: $e")
                        }
                    }
                    callback(false, errorMessages)
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("RegisterFailure", "Network error: ${t.message}")
                callback(false, listOf("Không thể kết nối đến server: ${t.message}"))
            }
        })
    }
}