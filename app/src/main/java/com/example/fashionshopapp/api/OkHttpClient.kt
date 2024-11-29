package com.example.fashionshopapp.api

import android.content.Context
import com.example.fashionshopapp.utils.isNetworkAvailable
import okhttp3.Cache
import okhttp3.OkHttpClient

fun provideOkHttpClient(context: Context): OkHttpClient {
    val cacheSize = 10 * 1024 * 1024L
    val cache = Cache(context.cacheDir, cacheSize)

    return OkHttpClient.Builder()
        .cache(cache)
        .addInterceptor { chain ->
            val request = if (isNetworkAvailable(context)) {
                chain.request().newBuilder()
                    .header("Cache-Control", "public, max-age=60")
                    .build()
            } else {
                chain.request().newBuilder()
                    .header(
                        "Cache-Control",
                        "public, only-if-cached, max-stale=604800"
                    ) // Cache 7 ngày khi không có mạng
                    .build()
            }
            chain.proceed(request)
        }
        .build()
}
