# Badminton Court Booking System

Hệ thống đặt sân cầu lông với Spring Boot backend và React TypeScript frontend.

## Tính năng chính

### Cho Guest (Khách vãng lai)
- Xem thông tin các sân cầu lông
- Xem đánh giá và rating của sân
- Tìm kiếm sân theo tên
- Xem sân còn trống theo thời gian

### Cho Member (Thành viên)
- Tất cả tính năng của Guest
- Đăng nhập/Đăng ký tài khoản
- Đặt sân cầu lông
- Thanh toán online/offline
- Áp dụng coupon giảm giá
- Thuê vợt (30k/cây)
- Viết review và rating sân
- Xem lịch sử booking

### Cho Staff (Nhân viên)
- Quản lý các sân (thêm, sửa, xóa)
- Set trạng thái sân (available, unavailable, maintenance)
- Tính tiền cho khách (thanh toán offline)
- Quản lý cho thuê vợt
- Xem lịch sử giao dịch

### Cho Owner (Chủ sở hữu)
- Tất cả tính năng của Staff
- Quản lý tài khoản Member và Staff
- Suspend/Active tài khoản
- Tạo tài khoản Staff
- Xem báo cáo doanh thu theo tháng
- Xem lịch sử giao dịch chi tiết

## Công nghệ sử dụng

### Backend
- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Security** (Authentication & Authorization)
- **Spring Data JPA** (Database ORM)
- **Microsoft SQL Server** (Database)
- **Maven** (Build tool)

### Frontend
- **React 18** với **TypeScript**
- **React Router DOM** (Routing)
- **Axios** (HTTP Client)
- **CSS Modules** (Styling)

## Cài đặt và chạy dự án

### Yêu cầu hệ thống
- Java 17+
- Node.js 16+
- Microsoft SQL Server hoặc để Spring Boot tự tạo database

### 1. Cài đặt dependencies

#### Backend
```bash
cd backend
./mvnw clean install
```

#### Frontend
```bash
cd frontend
npm install
```

### 2. Cấu hình Database

Cập nhật file `backend/src/main/resources/application.properties`:

```properties
# Database Configuration (Tùy chọn - Spring Boot có thể tự tạo DB)
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=badminton_booking;encrypt=false;trustServerCertificate=true
spring.datasource.username=sa
spring.datasource.password=YOUR_PASSWORD
```

### 3. Chạy ứng dụng

#### Cách 1: Chạy script tự động
```bash
# Chạy backend
chmod +x start-backend.sh
./start-backend.sh

# Chạy frontend (terminal mới)
chmod +x start-frontend.sh
./start-frontend.sh
```

#### Cách 2: Chạy thủ công

**Backend:**
```bash
cd backend
./mvnw spring-boot:run
```

**Frontend:**
```bash
cd frontend
npm run dev
```

### 4. Truy cập ứng dụng

- **Frontend:** http://localhost:3000
- **Backend API:** http://localhost:8080/api

## API Endpoints

### Authentication
- `POST /api/auth/login` - Đăng nhập
- `POST /api/auth/register` - Đăng ký
- `POST /api/auth/logout` - Đăng xuất

### Courts (Public)
- `GET /api/courts/public` - Lấy danh sách sân (guest)
- `GET /api/courts/public/{id}` - Chi tiết sân (guest)
- `GET /api/courts/public/available` - Sân còn trống (guest)
- `GET /api/courts/public/search` - Tìm kiếm sân (guest)

### Courts (Protected)
- `GET /api/courts` - Lấy tất cả sân (member+)
- `POST /api/courts` - Tạo sân mới (staff+)
- `PUT /api/courts/{id}` - Cập nhật sân (staff+)
- `PUT /api/courts/{id}/status` - Cập nhật trạng thái (owner)
- `DELETE /api/courts/{id}` - Xóa sân (owner)

### Bookings
- `POST /api/bookings` - Đặt sân (member+)
- `GET /api/bookings/my` - Booking của tôi (member+)
- `GET /api/bookings` - Tất cả booking (staff+)
- `PUT /api/bookings/{id}/status` - Cập nhật trạng thái (staff+)

### Reviews
- `GET /api/reviews/public/court/{courtId}` - Đánh giá sân (public)
- `POST /api/reviews` - Tạo đánh giá (member+)
- `PUT /api/reviews/{id}` - Cập nhật đánh giá (member)
- `DELETE /api/reviews/{id}` - Xóa đánh giá (member)

### Admin (Owner only)
- `GET /api/admin/users` - Quản lý users
- `PUT /api/admin/users/{id}/role` - Cập nhật role
- `PUT /api/admin/users/{id}/status` - Suspend/Active user

## Cấu trúc dự án

```
badminton-booking/
├── backend/
│   ├── src/main/java/com/badminton/booking/
│   │   ├── entity/          # Database entities
│   │   ├── dto/             # Data Transfer Objects
│   │   ├── repository/      # JPA Repositories
│   │   ├── service/         # Service interfaces
│   │   ├── serviceimpl/     # Service implementations
│   │   ├── controller/      # REST Controllers
│   │   ├── config/          # Configuration classes
│   │   └── security/        # Security configurations
│   ├── src/main/resources/
│   │   └── application.properties
│   └── pom.xml
├── frontend/
│   ├── src/
│   │   ├── components/      # React components
│   │   ├── pages/           # Page components
│   │   ├── services/        # API services
│   │   ├── contexts/        # React contexts
│   │   ├── types/           # TypeScript types
│   │   └── utils/           # Utility functions
│   ├── public/
│   └── package.json
├── start-backend.sh
├── start-frontend.sh
└── README.md
```

## Tài khoản mặc định

Sau khi chạy ứng dụng lần đầu, bạn có thể:

1. **Đăng ký tài khoản mới** qua giao diện web
2. **Tạo tài khoản Owner** qua database để quản lý hệ thống

## Đóng góp

1. Fork repository
2. Tạo feature branch (`git checkout -b feature/amazing-feature`)
3. Commit changes (`git commit -m 'Add amazing feature'`)
4. Push to branch (`git push origin feature/amazing-feature`)
5. Tạo Pull Request

## License

Dự án này được phân phối dưới giấy phép MIT.
