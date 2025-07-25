# 🏸 Hệ Thống Đặt Sân Cầu Lông

Một ứng dụng web hoàn chỉnh để đặt sân cầu lông với giao diện đẹp và tính năng đầy đủ.

## 🎯 Tính Năng

### 👥 Dành cho Khách Hàng
- 🏠 **Trang chủ**: Xem danh sách sân, tìm kiếm và lọc sân
- 📅 **Đặt sân**: Chọn sân, thời gian và thông tin đặt sân
- 📋 **Lịch sử**: Xem lịch sử đặt sân, trạng thái booking
- 🔐 **Đăng nhập/Đăng ký**: Quản lý tài khoản cá nhân
- 💳 **Thanh toán**: Hỗ trợ nhiều hình thức thanh toán
- ❌ **Hủy đặt sân**: Hủy booking với lý do

### 🏢 Dành cho Chủ Sân
- 📊 **Dashboard**: Thống kê doanh thu, lượng booking
- 🏟️ **Quản lý sân**: Thêm, sửa, xóa thông tin sân
- 📅 **Quản lý lịch**: Xem lịch đặt sân theo ngày/tuần/tháng
- ✅ **Xác nhận booking**: Duyệt hoặc từ chối đặt sân
- 💰 **Quản lý thanh toán**: Theo dõi các giao dịch

## 🛠️ Công Nghệ Sử Dụng

### Frontend
- **React 19** - Framework JavaScript hiện đại
- **React Router** - Điều hướng trang
- **TailwindCSS** - Framework CSS utility-first
- **Bootstrap** - Component UI
- **Axios/Fetch** - Gọi API

### Backend  
- **Spring Boot 3** - Framework Java
- **Spring Security** - Bảo mật và Authentication
- **Spring Data JPA** - ORM và Database
- **JWT** - JSON Web Token cho authentication
- **SQL Server** - Cơ sở dữ liệu
- **Swagger/OpenAPI** - Tài liệu API
- **Maven** - Quản lý dependency

## 🚀 Hướng Dẫn Chạy Ứng Dụng

### Yêu Cầu Hệ Thống
- **Java 21+**
- **Node.js 18+**
- **SQL Server** (hoặc LocalDB)
- **Maven 3.6+**
- **Git**

### 1. Clone Repository
```bash
git clone <repository-url>
cd badminton-booking-system
```

### 2. Cấu Hình Database
1. Cài đặt SQL Server hoặc SQL Server Express
2. Tạo database tên `CMS_System`
3. Cập nhật thông tin kết nối trong `Back end/cms-system/src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=CMS_System;encrypt=false;trustServerCertificate=true
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### 3. Chạy Backend
```bash
# Cách 1: Sử dụng script
./start-backend.sh

# Cách 2: Chạy thủ công
cd "Back end/cms-system"
./mvnw spring-boot:run
```

Backend sẽ chạy tại: `http://localhost:8080`
API Documentation: `http://localhost:8080/swagger-ui.html`

### 4. Chạy Frontend
```bash
# Terminal mới - Cách 1: Sử dụng script
./start-frontend.sh

# Cách 2: Chạy thủ công
cd "Front end/cms-fe"
npm install
npm start
```

Frontend sẽ chạy tại: `http://localhost:3000`

## 📱 Giao Diện Ứng Dụng

### Trang Chủ
- Hiển thị danh sách sân cầu lông với hình ảnh, giá cả, đánh giá
- Tìm kiếm sân theo tên, địa chỉ, quận
- Lọc sân theo giá, vị trí, đánh giá
- Xem chi tiết sân và lịch trống

### Trang Đặt Sân
- Chọn sân từ danh sách
- Chọn ngày và giờ đặt sân
- Nhập thông tin khách hàng
- Xác nhận và thanh toán

### Trang Lịch Sử
- Xem tất cả booking đã đặt
- Trạng thái: Chờ xác nhận, Đã xác nhận, Đã hủy, Hoàn thành
- Chi tiết từng booking
- Hủy booking (nếu được phép)

## 🔧 API Endpoints

### Courts API
- `GET /api/courts` - Lấy danh sách tất cả sân
- `GET /api/courts/{id}` - Lấy thông tin chi tiết sân
- `GET /api/courts/search?keyword={keyword}` - Tìm kiếm sân
- `GET /api/courts/filter` - Lọc sân theo tiêu chí

### Bookings API
- `POST /api/bookings` - Tạo booking mới (có đăng nhập)
- `POST /api/bookings/guest` - Tạo booking khách (không đăng nhập)
- `GET /api/bookings/my-bookings` - Lấy booking của user
- `GET /api/bookings/code/{code}` - Lấy booking theo mã
- `PUT /api/bookings/{id}/confirm` - Xác nhận booking
- `PUT /api/bookings/{id}/cancel` - Hủy booking
- `GET /api/bookings/available-slots` - Lấy giờ trống của sân

### Authentication API
- `POST /api/auth/login` - Đăng nhập
- `POST /api/auth/register` - Đăng ký
- `POST /api/auth/forgot-password` - Quên mật khẩu

## 💾 Cấu Trúc Database

### Bảng chính:
- **Account**: Thông tin người dùng
- **Court**: Thông tin sân cầu lông
- **Booking**: Thông tin đặt sân
- **Payment**: Thông tin thanh toán
- **Court_Schedule**: Lịch trình sân

### Mối quan hệ:
- User 1-N Booking
- Court 1-N Booking  
- Booking 1-1 Payment

## 🎨 Thiết Kế UI/UX

- **Responsive Design**: Tương thích mobile, tablet, desktop
- **Modern UI**: Sử dụng gradient, shadow, animation
- **User-friendly**: Giao diện trực quan, dễ sử dụng
- **Loading States**: Hiển thị trạng thái tải dữ liệu
- **Error Handling**: Xử lý lỗi một cách đẹp mắt

## 🔒 Bảo Mật

- **JWT Authentication**: Bảo mật API endpoints
- **Input Validation**: Kiểm tra dữ liệu đầu vào
- **SQL Injection Protection**: Sử dụng JPA/Hibernate
- **CORS Configuration**: Cấu hình Cross-Origin
- **Password Encryption**: Mã hóa mật khẩu

## 📞 Hỗ Trợ

Nếu gặp vấn đề khi chạy ứng dụng:

1. **Kiểm tra phiên bản Java**: `java -version`
2. **Kiểm tra phiên bản Node.js**: `node -v`
3. **Kiểm tra kết nối database**
4. **Xem log lỗi** trong terminal

## 🤝 Đóng Góp

Hoan nghênh mọi đóng góp để cải thiện dự án:

1. Fork repository
2. Tạo feature branch
3. Commit changes
4. Push to branch  
5. Tạo Pull Request

## 📄 License

Dự án này được phát hành dưới license MIT.

---

**Chúc bạn sử dụng ứng dụng vui vẻ! 🏸✨**
