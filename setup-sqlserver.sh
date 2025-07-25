#!/bin/bash

echo "🔧 Cài đặt SQL Server cho dự án Badminton Booking System..."

# Cài đặt Docker
echo "📦 Cài đặt Docker..."
sudo apt-get update
sudo apt-get install -y apt-transport-https ca-certificates curl gnupg lsb-release

# Thêm Docker's official GPG key
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg

# Thêm Docker repository
echo "deb [arch=$(dpkg --print-architecture) signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null

# Cài đặt Docker Engine
sudo apt-get update
sudo apt-get install -y docker-ce docker-ce-cli containerd.io docker-compose-plugin

# Khởi động Docker service
sudo systemctl start docker
sudo systemctl enable docker

# Thêm user hiện tại vào docker group
sudo usermod -aG docker $USER

echo "🐳 Docker đã được cài đặt thành công!"

# Cài đặt Docker Compose (standalone)
echo "📦 Cài đặt Docker Compose..."
sudo curl -L "https://github.com/docker/compose/releases/download/v2.20.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
sudo chmod +x /usr/local/bin/docker-compose

echo "🚀 Khởi động SQL Server container..."

# Khởi động SQL Server
docker-compose up -d

echo "⏳ Đợi SQL Server khởi động..."
sleep 30

echo "✅ SQL Server đã sẵn sàng!"
echo "📍 Connection info:"
echo "   Server: localhost:1433"
echo "   Username: sa"
echo "   Password: Password123"
echo "   Database: badminton_booking (sẽ được tạo tự động)"

echo ""
echo "🎯 Bây giờ bạn có thể chạy Spring Boot application:"
echo "   cd backend"
echo "   ./mvnw clean spring-boot:run"