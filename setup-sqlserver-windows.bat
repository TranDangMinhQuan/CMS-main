@echo off
echo 🔧 Cài đặt SQL Server cho dự án Badminton Booking System trên Windows...

:: Kiểm tra Docker Desktop
echo 📦 Kiểm tra Docker Desktop...
docker --version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Docker Desktop không được cài đặt!
    echo 📥 Vui lòng tải và cài đặt Docker Desktop từ: https://www.docker.com/products/docker-desktop
    echo    Sau khi cài đặt xong, chạy lại script này.
    pause
    exit /b 1
)

echo ✅ Docker Desktop đã được cài đặt!

:: Kiểm tra Docker đang chạy
echo 🔍 Kiểm tra Docker đang chạy...
docker info >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Docker Desktop chưa được khởi động!
    echo 🚀 Vui lòng khởi động Docker Desktop và chạy lại script này.
    pause
    exit /b 1
)

echo ✅ Docker Desktop đang chạy!

echo 🚀 Khởi động SQL Server container...

:: Tạo và chạy SQL Server container
docker run -e "ACCEPT_EULA=Y" -e "SA_PASSWORD=Password123" -p 1433:1433 --name badminton_sqlserver -d mcr.microsoft.com/mssql/server:2019-latest

if %errorlevel% neq 0 (
    echo ❌ Lỗi khi khởi động SQL Server container!
    echo 🔄 Thử xóa container cũ và tạo lại...
    docker rm -f badminton_sqlserver >nul 2>&1
    docker run -e "ACCEPT_EULA=Y" -e "SA_PASSWORD=Password123" -p 1433:1433 --name badminton_sqlserver -d mcr.microsoft.com/mssql/server:2019-latest
)

echo ⏳ Đợi SQL Server khởi động (30 giây)...
timeout /t 30 /nobreak >nul

echo ✅ SQL Server đã sẵn sàng!
echo.
echo 📍 Thông tin kết nối:
echo    Server: localhost:1433
echo    Username: sa
echo    Password: Password123
echo    Database: badminton_booking (sẽ được tạo tự động)
echo.
echo 🎯 Bây giờ bạn có thể chạy Spring Boot application:
echo    cd backend
echo    mvnw.cmd clean spring-boot:run
echo.
echo 🌐 Hoặc sử dụng script khởi động:
echo    start-backend-windows.bat

pause