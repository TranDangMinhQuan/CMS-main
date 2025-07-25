@echo off
echo 🧪 Test các cấu hình SQL Server khác nhau...
echo.

cd backend

echo 📋 Chọn cấu hình SQL Server:
echo.
echo 1. Default (localhost:1433) - SQL Authentication
echo 2. SQL Express (localhost\SQLEXPRESS) - SQL Authentication  
echo 3. Windows Authentication (localhost:1433)
echo 4. Custom configuration
echo.

set /p choice="Nhập lựa chọn (1-4): "

if "%choice%"=="1" (
    echo 🔧 Test với default configuration...
    mvnw.cmd spring-boot:run
) else if "%choice%"=="2" (
    echo 🔧 Test với SQL Express configuration...
    mvnw.cmd spring-boot:run -Dspring.profiles.active=sqlexpress
) else if "%choice%"=="3" (
    echo 🔧 Test với Windows Authentication...
    mvnw.cmd spring-boot:run -Dspring.profiles.active=sqlserver-local
) else if "%choice%"=="4" (
    echo 💡 Hướng dẫn tùy chỉnh:
    echo.
    echo Sửa file: backend/src/main/resources/application.properties
    echo.
    echo Thay đổi:
    echo spring.datasource.url=jdbc:sqlserver://[SERVER]:[PORT];databaseName=badminton_booking;encrypt=false;trustServerCertificate=true
    echo spring.datasource.username=[USERNAME]
    echo spring.datasource.password=[PASSWORD]
    echo.
    echo Sau đó chạy: mvnw.cmd spring-boot:run
    pause
) else (
    echo ❌ Lựa chọn không hợp lệ!
    pause
)

echo.
echo 💡 Tips:
echo - Kiểm tra SQL Server đang chạy
echo - Kiểm tra port (thường là 1433)
echo - Kiểm tra username/password
echo - Nếu dùng named instance: localhost\SQLEXPRESS
echo - Kiểm tra firewall không chặn port 1433