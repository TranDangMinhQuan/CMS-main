@echo off
echo 🔍 Kiểm tra hệ thống cho dự án Badminton Booking System...
echo.

:: Kiểm tra Java
echo ☕ Kiểm tra Java...
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Java không được cài đặt!
    echo 📥 Vui lòng cài đặt Java 17+ từ: https://adoptium.net/
) else (
    echo ✅ Java đã được cài đặt:
    java -version
)
echo.

:: Kiểm tra Node.js
echo 📦 Kiểm tra Node.js...
node --version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Node.js không được cài đặt!
    echo 📥 Vui lòng cài đặt Node.js 16+ từ: https://nodejs.org/
) else (
    echo ✅ Node.js đã được cài đặt:
    node --version
)
echo.

:: Kiểm tra npm
echo 📦 Kiểm tra npm...
npm --version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ npm không được cài đặt!
) else (
    echo ✅ npm đã được cài đặt:
    npm --version
)
echo.

:: Kiểm tra Docker
echo 🐳 Kiểm tra Docker Desktop...
docker --version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Docker Desktop không được cài đặt!
    echo 📥 Vui lòng cài đặt Docker Desktop từ: https://www.docker.com/products/docker-desktop
) else (
    echo ✅ Docker Desktop đã được cài đặt:
    docker --version
    
    :: Kiểm tra Docker đang chạy
    docker info >nul 2>&1
    if %errorlevel% neq 0 (
        echo ⚠️  Docker Desktop chưa được khởi động!
        echo 🚀 Vui lòng khởi động Docker Desktop.
    ) else (
        echo ✅ Docker Desktop đang chạy!
    )
)
echo.

:: Kiểm tra Git
echo 📚 Kiểm tra Git...
git --version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Git không được cài đặt!
    echo 📥 Vui lòng cài đặt Git từ: https://git-scm.com/download/win
) else (
    echo ✅ Git đã được cài đặt:
    git --version
)
echo.

echo 🎯 Tóm tắt:
echo    - Java 17+: Cần thiết cho Spring Boot backend
echo    - Node.js 16+: Cần thiết cho React frontend  
echo    - Docker Desktop: Cần thiết cho SQL Server
echo    - Git: Cần thiết cho version control
echo.
echo 💡 Sau khi cài đặt đầy đủ, chạy: setup-sqlserver-windows.bat

pause