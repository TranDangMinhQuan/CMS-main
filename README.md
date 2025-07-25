# 🏸 Hệ thống Đặt sân Cầu lông

Hệ thống quản lý đặt sân cầu lông với đầy đủ tính năng cho Guest, Member, Staff và Owner.

## 🌟 Tính năng chính

### 👥 Vai trò và Quyền hạn

#### 🔗 **Guest (Khách vãng lai)**
- Xem thông tin sân cầu lông
- Xem reviews và ratings của các sân
- Tìm kiếm sân theo tên
- Xem lịch sân còn trống

#### 👤 **Member (Thành viên)**
- Tất cả tính năng của Guest
- Đăng ký và đăng nhập tài khoản
- Đặt sân với lựa chọn thanh toán online/offline
- Áp dụng mã giảm giá (coupon)
- Thuê vợt cầu lông (30,000 VNĐ/cây)
- Viết review và đánh giá sân
- Xem lịch sử đặt sân của bản thân

#### 👨‍💼 **Staff (Nhân viên)**
- Quản lý trạng thái sân (available/unavailable)
- Xử lý thanh toán trực tiếp tại sân
- Quản lý dịch vụ cho thuê vợt
- Tính toán phí dựa trên số giờ chơi
- Xem báo cáo giao dịch theo ngày/tháng/năm

#### 👑 **Owner (Chủ sở hữu)**
- Tất cả quyền của Staff
- Quản lý tài khoản Member và Staff
- Tạo tài khoản cho Staff
- Suspend/Resume tài khoản Member và Staff
- Quản lý sân (thêm/sửa/xóa, bảo trì)
- Xem báo cáo doanh thu chi tiết theo tháng
- Quản lý hệ thống coupon

## 🛠️ Công nghệ sử dụng

### Backend
- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Security** (JWT Authentication)
- **Spring Data JPA** (ORM)
- **Microsoft SQL Server** (Database)
- **Maven** (Build tool)
- **Hibernate** (JPA Implementation)

### Frontend
- **React 18**
- **TypeScript**
- **React Router DOM**
- **Axios** (HTTP Client)
- **CSS Modules**
- **Modern Responsive Design**

## 🚀 Cài đặt và Chạy

### Yêu cầu hệ thống
- Java 17+
- Node.js 16+
- Docker và Docker Compose (cho SQL Server)
- Git

### 🐳 Bước 1: Cài đặt SQL Server

#### 🪟 **Trên Windows:**

```batch
:: Chạy script tự động
setup-sqlserver-windows.bat
```

**Hoặc cài đặt thủ công:**

1. **Cài đặt Docker Desktop**: 
   - Tải từ: https://www.docker.com/products/docker-desktop
   - Cài đặt và khởi động Docker Desktop

2. **Chạy SQL Server container:**
```cmd
docker run -e "ACCEPT_EULA=Y" -e "SA_PASSWORD=Password123" -p 1433:1433 --name badminton_sqlserver -d mcr.microsoft.com/mssql/server:2019-latest
```

#### 🐧 **Trên Linux:**

```bash
# Cài đặt Docker và SQL Server tự động
./setup-sqlserver.sh
```

**Hoặc cài đặt thủ công:**

```bash
# Cài đặt Docker
sudo apt-get update
sudo apt-get install docker.io docker-compose

# Khởi động SQL Server container
docker-compose up -d

# Kiểm tra container đang chạy
docker ps
```

### 🗄️ Thông tin Database
- **Server**: localhost:1433
- **Username**: sa  
- **Password**: Password123
- **Database**: badminton_booking (tự động tạo)

### ⚙️ Bước 2: Chạy Backend

#### 🪟 **Trên Windows:**
```cmd
cd backend
mvnw.cmd clean spring-boot:run
```

#### 🐧 **Trên Linux:**
```bash
cd backend
./mvnw clean spring-boot:run
```

### 🎨 Bước 3: Chạy Frontend

#### 🪟 **Trên Windows:**
```cmd
cd frontend
npm install
npm run dev
```

#### 🐧 **Trên Linux:**
```bash
cd frontend
npm install
npm run dev
```

### 🔧 Hoặc sử dụng scripts tự động

#### 🪟 **Trên Windows:**
```batch
:: Command Prompt 1 - Backend
start-backend-windows.bat

:: Command Prompt 2 - Frontend
start-frontend-windows.bat
```

#### 🐧 **Trên Linux:**
```bash
# Terminal 1 - Backend
./start-backend.sh

# Terminal 2 - Frontend
./start-frontend.sh
```

## 🌐 Truy cập ứng dụng

- **Frontend**: http://localhost:3000
- **Backend API**: http://localhost:8080/api
- **H2 Console**: http://localhost:8080/h2-console (nếu dùng H2)

## 📋 API Endpoints

### Xác thực
- `POST /api/auth/register` - Đăng ký tài khoản
- `POST /api/auth/login` - Đăng nhập

### Sân cầu lông (Public)
- `GET /api/courts/public` - Xem danh sách sân
- `GET /api/courts/public/{id}` - Xem chi tiết sân
- `GET /api/courts/public/available` - Xem sân còn trống
- `GET /api/courts/public/search` - Tìm kiếm sân

### Quản lý sân (Protected)
- `POST /api/courts` - Tạo sân mới (Staff/Owner)
- `PUT /api/courts/{id}` - Cập nhật sân (Staff/Owner)
- `PUT /api/courts/{id}/status` - Cập nhật trạng thái sân (Owner)
- `DELETE /api/courts/{id}` - Xóa sân (Owner)

### Đặt sân
- `POST /api/bookings` - Đặt sân (Member+)
- `GET /api/bookings` - Xem danh sách đặt sân
- `PUT /api/bookings/{id}/status` - Cập nhật trạng thái (Staff+)
- `DELETE /api/bookings/{id}` - Hủy đặt sân

### Reviews
- `GET /api/reviews/public/court/{courtId}` - Xem reviews của sân
- `POST /api/reviews` - Viết review (Member+)

### Quản lý người dùng (Admin)
- `GET /api/admin/users` - Danh sách người dùng (Owner)
- `PUT /api/admin/users/{id}/role` - Thay đổi vai trò (Owner)
- `PUT /api/admin/users/{id}/status` - Suspend/Resume (Owner)

## 🗂️ Cấu trúc dự án

```
badminton-booking/
├── backend/                 # Spring Boot application
│   ├── src/main/java/
│   │   └── com/badminton/booking/
│   │       ├── entity/      # JPA Entities
│   │       ├── repository/  # Data repositories
│   │       ├── service/     # Business logic
│   │       ├── controller/  # REST controllers
│   │       ├── dto/         # Data Transfer Objects
│   │       └── config/      # Configurations
│   └── src/main/resources/
│       └── application.properties
├── frontend/               # React application
│   ├── public/
│   ├── src/
│   │   ├── components/     # Reusable components
│   │   ├── pages/          # Page components
│   │   ├── services/       # API services
│   │   ├── contexts/       # React contexts
│   │   ├── types/          # TypeScript types
│   │   └── utils/          # Utility functions
│   └── package.json
├── docker-compose.yml      # SQL Server container
├── setup-sqlserver.sh      # SQL Server setup script
├── start-backend.sh        # Backend startup script
├── start-frontend.sh       # Frontend startup script
└── README.md
```

## 🧪 Tài khoản mặc định

Sau khi chạy ứng dụng, bạn có thể tạo tài khoản hoặc sử dụng API để tạo tài khoản Owner đầu tiên.

## 🛠️ Khắc phục sự cố

### Backend không kết nối được database
```bash
# Kiểm tra SQL Server container
docker ps

# Xem logs SQL Server
docker logs badminton_sqlserver

# Restart SQL Server
docker-compose restart
```

### Frontend không call được API
- Kiểm tra CORS configuration trong `application.properties`
- Đảm bảo backend đang chạy trên port 8080
- Kiểm tra URL trong `frontend/src/services/api.ts`

### Port đã được sử dụng
```bash
# Kiểm tra port đang sử dụng
sudo netstat -tulpn | grep :8080
sudo netstat -tulpn | grep :3000
sudo netstat -tulpn | grep :1433

# Kill process nếu cần
sudo kill -9 <PID>
```

## 🤝 Đóng góp

1. Fork repository
2. Tạo feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to branch (`git push origin feature/AmazingFeature`)
5. Tạo Pull Request

## 📄 License

Distributed under the MIT License. See `LICENSE` for more information.

## 📞 Liên hệ

Email: support@badmintonbooking.com
Website: https://badmintonbooking.com
