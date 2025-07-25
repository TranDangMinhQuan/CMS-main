#!/bin/bash

echo "Starting Badminton Booking Backend..."

cd backend

# Check if mvn is available
if ! command -v mvn &> /dev/null; then
    echo "Maven is not installed. Please install Maven first."
    exit 1
fi

# Start the Spring Boot application
echo "Running Spring Boot application..."
mvn spring-boot:run

echo "Backend started successfully!"