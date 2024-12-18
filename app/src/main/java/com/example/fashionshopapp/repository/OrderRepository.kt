package com.example.fashionshopapp.repository


import com.example.fashionshopapp.api.RetrofitInstance
import com.example.fashionshopapp.models.CreateOrderRequest
import com.example.fashionshopapp.models.Order
import okhttp3.ResponseBody
import retrofit2.Response

class OrderRepository() {

    suspend fun createOrder(order: CreateOrderRequest): Response<Order> {
        return try {
            val response = RetrofitInstance.api.createOrder(order)
            println("Dữ liệu gửi lên server: $order")
            if (response.isSuccessful) {
                // Trả về Response thành công và chứa đối tượng Order
                println("Đơn hàng đã được lưu thành công.")
                response
            } else {
                val errorBody = response.errorBody()?.string()
                println("Lỗi từ server (${response.code()}): $errorBody")
                // Trả về Response thất bại với mã lỗi
            }
            response
        } catch (e: Exception) {
            println("Lỗi khi gọi API: ${e.localizedMessage}")
            // Trả về Response thất bại nếu có lỗi trong quá trình gọi API
            Response.error(500, ResponseBody.create(null, "Lỗi khi gọi API"))
        }
    }
}