package com.example.fashionshopapp.api

data class LoginRequest(
    val username: String,
    val password: String
)
data class LoginResponse(
    val token: String,
    val userId: String
)

data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String,
    val fullName: String
)

data class RegisterResponse(
    val success: Boolean,
    val message: String
)
