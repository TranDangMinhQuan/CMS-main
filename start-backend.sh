#!/bin/bash

echo "Starting Badminton Booking Backend..."

# Navigate to backend directory
cd backend

# Build and run the Spring Boot application
echo "Building and starting Spring Boot application..."
./mvnw clean spring-boot:run

echo "Backend started successfully!"