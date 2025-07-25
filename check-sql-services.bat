@echo off
echo 🔍 Kiểm tra SQL Server services trên Windows...
echo.

echo 📋 Kiểm tra SQL Server services đang chạy:
echo.
sc query "MSSQLSERVER" 2>nul | findstr "STATE" | findstr "RUNNING" >nul
if %errorlevel%==0 (
    echo ✅ MSSQLSERVER service đang chạy
    echo    Default Instance có thể kết nối qua: localhost:1433
) else (
    echo ❌ MSSQLSERVER service không chạy
)

sc query "MSSQL$SQLEXPRESS" 2>nul | findstr "STATE" | findstr "RUNNING" >nul
if %errorlevel%==0 (
    echo ✅ SQL Server Express service đang chạy  
    echo    Express Instance có thể kết nối qua: localhost\SQLEXPRESS
) else (
    echo ❌ SQL Server Express service không chạy
)

echo.
echo 📊 Tất cả SQL Server services:
sc query | findstr "MSSQL" | findstr "SERVICE_NAME"

echo.
echo 🔌 Kiểm tra port 1433 đang được sử dụng:
netstat -an | findstr ":1433" | findstr "LISTENING"
if %errorlevel%==0 (
    echo ✅ Port 1433 đang được sử dụng
) else (
    echo ❌ Port 1433 không được sử dụng
)

echo.
echo 💡 Gợi ý:
echo    1. Nếu không có service nào chạy, hãy khởi động SQL Server từ:
echo       - SQL Server Configuration Manager
echo       - Services.msc
echo       - SQL Server Management Studio
echo.
echo    2. Nếu chỉ có SQL Express, hãy sử dụng connection string:
echo       localhost\SQLEXPRESS thay vì localhost:1433
echo.
echo    3. Kiểm tra database CMS_system đã tồn tại chưa
echo.

pause