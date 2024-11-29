# Update Log

Tạo API cho Products, Category, Brand

Test ở Swagger

## Update 10/11/2024

Tối ưu hóa Program.cs
Sửa UseEndpoints - > app.MapControllerRoute & app.MapRazorPages: Phiên bản mới của .NET không yêu cầu UseEndpoints.

Sắp xếp lại middleware để tuân theo trình tự hợp lý của pipeline trong ASP.NET Core (UseCors, UseSession, UseAuthentication)

Tạo AuthController (3 API cơ bản) -> Run

Test Đăng xuất: OK

Khi test Đăng ký lỗi: cột FullName trong bảng AspNetUsers không cho phép giá trị NULL

1 -> Cho Fullname = "", cập nhật lại migrations - > Thất bại

2 -> Thêm Fullname vào Register Model, Cập nhật API Register để lưu FullName vào database -> Thành công

Test Đăng nhập lỗi: phương thức Encoding.UTF8.GetBytes() cố gắng xử lý một chuỗi null
-> Cấu hình Jwt ở appsettings.json

Key thay vì lưu trong appsettings.json (cô định) -> Sinh khóa ngẫu nhiên

Tiếp tục Đăng Nhập lại lỗi: HS256 (HMAC-SHA256) yêu cầu khóa ký có kích thước tối thiểu là 256 bit
-> Điều chỉnh hàm GenerateJwtToken (var key = new byte[32]; với 256 bit = 32 byte) -> Thành công

## Update 11/11/2024

Thêm thư viện Compose Navigation

Thêm BottomNavigationBar

Điều hướng sang 4 Screens

Đổi màu background BottomNavigationBar

## Update 12-13/11/2024

Tối ưu hóa giao diện Trang chủ (có khuyến mãi mới hiện giá trị khuyến mãi)

Fix lỗi màu giá

Tách ProfileScreen khỏi Main thành 1 class riêng

Demo profile

## Update 16/11/2024

Xây dựng register

Gọi @POST("api/auth/register")

Xây dựng model, repo đăng ký

Tạo màn hình đăng ký RegisterScreen

Cập nhật MainActivity, ProfileScreen

Test -> Thất bại -> Thử lại -> Thật bại

-> Xây UI hướng dẫn người dùng khi đăng ký thất bại

-> Kiểm tra API Swagger -> Không thấy lỗi => lỗi ở dữ liệu app truyền đi

-> Xây logcat trả về backend kiểm tra bug -> Lỗi dữ liệu gửi đi bị sai thứ tự

Lỗi trả về:
- username bị gán giá trị của fullName
- fullName bị gán giá trị của email
- email bị gán giá trị của password
- password bị gán giá trị của username

-> Ràng buộc dữ liệu khi truyền
val request = RegisterRequest(username = username, email = email, password = password, fullName = fullName)

Kiểm tra Request data -> Đúng

=> Đăng ký thành công

=> Login bằng tài khoản vừa đăng ký thành công

## Update 17/11/2024
- Register-v1 | "Show bug UI"

Báo lỗi giao diện đăng ký ra để người dùng biết, file Error tạo ra nhưng chưa dịch mã lỗi sang Vietnamese

## Update 18/11/2024

Thêm giỏ hàng bào nav trang 3 nhưng bị lỗi reset dữ liệu khi chuyển tiếp các trang

## Update 19/11/2024

Fix bug giỏ hàng nhưng không thành công

## Update 20/11/2024

Bỏ toàn bộ code cũ, rabase về nhánh Register-V1 và code lại toàn bộ giỏ hàng -> Thành công

## Update 21/11/2024

Add bug log vào issue (3)

## Update 22/11/2024

Chỉnh sửa logic giảm giá để có thể nhận trong giỏ hàng, bỏ các thuộc tính dư ra từ giỏ hàng .0f để dễ nhận diện

## Update 23/11/2024

Cập nhật format cho price (.3f). Bỏ các đối tượng không cần thiết 

Cập nhật chức năng checkout từ Cart. get giá trị đơn hàng từ Car

## Update 25/11/2024

Tách 3 giao diện Auth ra để quản lí

Design trang Đăng ký

Cấu hình OKHttpClient? - Thời gian phản hồi vẫn quá lâu

## Update 26/11/2024

Cấu hình [Open Weather](https://openweathermap.org/) nhưng lấy dữ liệu trả về bị sai, mặc định là [Mountain View](https://api.openweathermap.org/data/2.5/weather?lat=37.4219983&lon=-122.084&appid=0960e4a5c0a7f890a664fbd6a4e4ed70&units=metric) dù đã fix hơn 6 tiếng
=> Không được pull vào nhánh

## Update 27/11/2024

Tiếp tục fix nhưng không thành công => chuyển sang dùng http của ip-api để kiểm tra vị trí

Logic yêu cầu thanh toán khi đã đăng nhập (11:29)

## Update 28/11/2024:

Tối ưu hệ thống

Điều chỉnh logic cart (vẫn chưa nhận giá trị Islogin)

## Update 29/11/2024:

Fix logic lấy giá trị isLogin ở CartScreen từ Profile. Đảm bảo không chạy vòng lặp đăng nhập ở dialog

Bổ sung 1 số thư viện cần thiết

## Update 30/11/2024:

[02:04] Tích hợp cả [Open Weather](https://openweathermap.org/) lấy tọa độ x, y tương ứng với [ip-api](http://ip-api.com/json/). Đưa ra lời khuyên dựa theo thời tiết hiện tại

Thiết kế giao diện hiển thị WeatherScreen
