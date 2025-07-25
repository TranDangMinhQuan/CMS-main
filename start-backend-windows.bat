@echo off
echo 🚀 Khởi động Backend (Spring Boot)...

cd backend

echo 📦 Đang build và chạy Spring Boot application...
mvnw.cmd clean spring-boot:run

pause