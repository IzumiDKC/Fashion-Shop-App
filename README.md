# Update Log

## Thời gian thực hiện đồ án: 30/09/2024 – ?/12/2024

---

## Tuần 1: 30/09 – 06/10/2024
- **Khởi động dự án:**
  - Xây dựng cấu trúc cơ bản cho dự án.
  - Thiết lập môi trường phát triển (ASP.NET Core, Android Compose, Swagger).
  - Lên kế hoạch cho các tính năng chính: Products, Category, Brand.

---

## Tuần 2: 07/10 – 13/10/2024
- **Tạo các API cơ bản:**
  - Tạo API cho Products, Category, Brand.
  - Test API trên Swagger – **Hoàn thành.**
- **Thiết kế giao diện cơ bản:**
  - Bắt đầu xây dựng giao diện với Android Compose.

---

## Tuần 3: 14/10 – 20/10/2024
- **Tích hợp Swagger vào project.**
- Tối ưu giao diện:
  - Cải tiến cấu trúc BottomNavigationBar.
  - Điều hướng giữa các màn hình cơ bản.

---

## Tuần 4: 21/10 – 27/10/2024
- **Xây dựng các API liên quan đến người dùng:**
  - Tạo AuthController với 3 API cơ bản: Login, Register, Logout.
- **Test API:**
  - **Login:** Gặp lỗi JWT (chuỗi null).
  - **Register:** Lỗi cột `FullName` không cho phép NULL.
- **Fix lỗi:**
  - Cấu hình `Jwt` với khóa ngẫu nhiên.
  - Thêm `FullName` vào Register Model – **Thành công.**

---

## Tuần 5: 28/10 – 03/11/2024
- Tối ưu code trong `Program.cs`.
- Sắp xếp middleware theo thứ tự hợp lý:
  - `UseCors`, `UseSession`, `UseAuthentication`.
- **Thiết kế UI:**
  - Cải tiến giao diện `HomeScreen` (hiển thị giá trị khuyến mãi, fix lỗi hiển thị màu giá).

---

## Tuần 6: 04/11 – 10/11/2024
- **Phát triển ProfileScreen:**
  - Tách `ProfileScreen` thành class riêng.
  - Demo giao diện hồ sơ cơ bản – **Hoàn thành.**
- **Tối ưu hóa giao diện:**
  - Thêm BottomNavigationBar với 4 màn hình điều hướng.
  - Tùy chỉnh màu nền BottomNavigationBar.

---

## Tuần 7: 11/11 – 17/11/2024
- **Phát triển chức năng Đăng ký:**
  - Tạo `RegisterScreen` và kết nối API `@POST("api/auth/register")`.
  - Fix lỗi giao diện đăng ký không hiển thị lỗi rõ ràng.
  - Hoàn thiện đăng ký – **Thành công.**
- **Giỏ hàng:**
  - Thêm giỏ hàng vào Navigation, nhưng gặp lỗi reset dữ liệu khi chuyển giữa các trang.

---

## Tuần 8: 18/11 – 24/11/2024
- **Fix bug giỏ hàng:**
  - Bỏ toàn bộ code cũ, rabase về nhánh `Register-V1` và code lại từ đầu – **Thành công.**
- **Cập nhật logic giảm giá:**
  - Bỏ các thuộc tính dư thừa.
  - Điều chỉnh định dạng giá trị (`.3f`) để dễ dàng nhận diện.

---

## Tuần 9: 25/11 – 01/12/2024
- **Tích hợp Open Weather API:**
  - Kết nối OpenWeather và ip-api để lấy tọa độ.
  - Hiển thị lời khuyên dựa trên thời tiết hiện tại.
- **Thiết kế WeatherScreen:**
  - Tạo giao diện hiển thị thông tin thời tiết.

---

## Tuần 10: 02/12 – 08/12/2024
- **Cải thiện ProfileDetail:**
  - Thêm tính năng PUT để cập nhật thông tin cá nhân – **Hoàn thành.**
- **Tối ưu Firebase:**
  - Xóa các thư viện Firebase dư thừa.

---

## Tuần 11: 09/12 – 15/12/2024
- **Cập nhật database:**
  - Nội dung ngày 13/12/2024: [UpdatedDb](https://anotepad.com/notes/d4drqwsk)
  - Nội dung ngày 14/12/2024:
    +  [UpdatedDb](https://anotepad.com/notes/qymb4sh9)
    + Cải thiện truy vấn ở giữa 2 Table Products và Brands tại [đây](https://github.com/IzumiDKC/Fashion-Shop)
- **Cải thiện code**
- **Xây dựng API cùng các trang CategoryGrid**
  - CategoryGrid-Sale + Interface
  - CategoryGrid-Hot + Interface
  - CategoryGrid-FlashSale + Interface
  - CategoryGrid-ChristmasCollection + Interface
---

## Tuần 12: 16/12 – 22/12/2024
- **Xây dựng API cùng các trang CategoryGrid (2)**
  - CategoryGrid-Accessory: 16/12/2024
  - CategoryGrid-New-Arrival: 17/12/2024
  - Build Order
    + Create
    + Fix bug
    + OrderHistory + Interface
---
