package com.example.fashionshopapp.utils

import android.content.Context

fun saveTokenToPreferences(context: Context, token: String) {
    val sharedPref = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
    with(sharedPref.edit()) {
        putString("auth_token", token)
        apply()
    }
}
fun getTokenFromPreferences(context: Context): String? {
    val sharedPref = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
    return sharedPref.getString("auth_token", null)
}
fun clearTokenFromPreferences(context: Context) {
    val sharedPref = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
    with(sharedPref.edit()) {
        remove("auth_token")
        apply()
    }
}

