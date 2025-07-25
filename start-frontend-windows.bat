@echo off
echo 🎨 Khởi động Frontend (React)...

cd frontend

echo 📦 Cài đặt dependencies...
call npm install

echo 🚀 Khởi động React development server...
call npm run dev

pause