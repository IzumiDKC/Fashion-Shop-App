package com.example.fashionshopapp.repository

import android.util.Log
import com.example.fashionshopapp.api.LoginRequest
import com.example.fashionshopapp.api.LoginResponse
import com.example.fashionshopapp.api.RegisterRequest
import com.example.fashionshopapp.api.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
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
    fun register(username: String,
                 password: String,
                 fullName: String,
                 email: String,
                 callback: (Boolean) -> Unit)
    {

        val request = RegisterRequest(username = username, email = email, password = password, fullName = fullName)
        Log.d("RegisterDebug", "Request data: $request")


        RetrofitInstance.api.register(request).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    callback(true)
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("RegisterError", "Error: $errorBody")
                    callback(false)
                }
            }


            override fun onFailure(call: Call<Void>, t: Throwable) {
                callback(false)
            }
        })
    }

}
