@echo off
echo 🔍 Debug kết nối SQL Server...
echo.

echo 1️⃣ Kiểm tra SQL Server đang chạy...
netstat -an | findstr :1433
if %errorlevel% neq 0 (
    echo ❌ Port 1433 không được mở! SQL Server có thể chưa chạy.
) else (
    echo ✅ Port 1433 đang được sử dụng.
)
echo.

echo 2️⃣ Kiểm tra Docker container (nếu dùng Docker)...
docker ps | findstr badminton_sqlserver
if %errorlevel% neq 0 (
    echo ⚠️ Container SQL Server không chạy hoặc không tồn tại.
    echo 💡 Thử chạy: docker start badminton_sqlserver
    echo    Hoặc: setup-sqlserver-windows.bat
) else (
    echo ✅ Container SQL Server đang chạy.
)
echo.

echo 3️⃣ Ping SQL Server...
telnet localhost 1433 2>nul
if %errorlevel% neq 0 (
    echo ❌ Không thể kết nối đến localhost:1433
    echo 💡 Có thể:
    echo    - SQL Server chưa chạy
    echo    - Firewall chặn port 1433
    echo    - SQL Server chạy trên port khác
) else (
    echo ✅ Có thể kết nối đến port 1433
)
echo.

echo 4️⃣ Kiểm tra SQL Server processes...
tasklist | findstr sql
echo.

echo 🔧 CÁC GIẢI PHÁP THƯỜNG GẶP:
echo.
echo ❓ Nếu bạn đang dùng SQL Server Management Studio (SSMS):
echo    1. Mở SSMS
echo    2. Server name: localhost hoặc .\SQLEXPRESS hoặc localhost\SQLEXPRESS
echo    3. Authentication: SQL Server Authentication
echo    4. Login: sa
echo    5. Password: [your-password]
echo.
echo ❓ Nếu bạn đang dùng Docker:
echo    docker start badminton_sqlserver
echo.
echo ❓ Nếu SQL Server chạy trên named instance:
echo    Thay đổi trong application.properties:
echo    spring.datasource.url=jdbc:sqlserver://localhost:1433;instance=SQLEXPRESS;databaseName=badminton_booking
echo.
echo ❓ Nếu SQL Server có password khác:
echo    Thay đổi spring.datasource.password trong application.properties
echo.

echo 🎯 TESTING CONNECTION STRING:
echo Current: jdbc:sqlserver://localhost:1433;databaseName=badminton_booking;encrypt=false;trustServerCertificate=true
echo User: sa
echo Pass: Password123

pause