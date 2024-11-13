package com.example.fashionshopapp.repository

import com.example.fashionshopapp.api.LoginRequest
import com.example.fashionshopapp.api.LoginResponse
import com.example.fashionshopapp.api.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthRepository {
    fun login(username: String, password: String, onResult: (Boolean, String?) -> Unit) {
        val request = LoginRequest(username, password)
        RetrofitInstance.api.login(request).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val token = response.body()?.token
                    onResult(true, token)
                } else {
                    onResult(false, null)
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                onResult(false, null)
            }
        })
    }
}
