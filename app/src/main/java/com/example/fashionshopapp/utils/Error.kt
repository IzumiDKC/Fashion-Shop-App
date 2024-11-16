package com.example.fashionshopapp.utils

object ErrorUtils {
    fun getErrorMessage(code: String): String {
        return when (code) {
            "PasswordTooShort" -> "Mật khẩu phải có ít nhất 6 ký tự."
            "PasswordRequiresNonAlphanumeric" -> "Mật khẩu phải chứa ít nhất một ký tự đặc biệt."
            "PasswordRequiresDigit" -> "Mật khẩu phải chứa ít nhất một chữ số (0-9)."
            "PasswordRequiresUpper" -> "Mật khẩu phải chứa ít nhất một chữ cái in hoa (A-Z)."
            "PasswordRequiresLower" -> "Mật khẩu phải chứa ít nhất một chữ cái thường (a-z)."
            "PasswordRequiresUniqueChars" -> "Mật khẩu phải sử dụng ít nhất một ký tự khác nhau."
            "DuplicateUserName" -> "Tên đăng nhập đã được sử dụng."
            "DuplicateEmail" -> "Email đã được đăng ký."
            "InvalidEmail" -> "Email không hợp lệ."
            else -> "Lỗi không xác định: $code"
        }
    }
}
