#!/bin/bash

echo "🚀 Starting Badminton Court Management Backend..."

cd "Back end/cms-system"

echo "📦 Building the application..."
./mvnw clean compile

echo "🔄 Starting Spring Boot application..."
./mvnw spring-boot:run

echo "✅ Backend is running on http://localhost:8080"
echo "📖 API Documentation available at http://localhost:8080/swagger-ui.html"