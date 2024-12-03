package com.example.fashionshopapp.models

import com.google.gson.annotations.SerializedName

data class UserProfile(
    @SerializedName("userName")
    val username: String,
    val email: String,
    val fullName: String,
    val age: String?,
    val address: String,
    val phoneNumber: String
)
data class UpdatedProfileModel(
    val username: String,
    val email: String,
    val fullName: String,
    val age: String?,
    val address: String,
    val phoneNumber: String
)
