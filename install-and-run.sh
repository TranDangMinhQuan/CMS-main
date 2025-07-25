#!/bin/bash

echo "=== Badminton Booking System Setup ==="

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "Maven not found. Installing Maven..."
    
    # Update package list
    sudo apt update
    
    # Install Maven
    sudo apt install -y maven
    
    # Verify installation
    if command -v mvn &> /dev/null; then
        echo "Maven installed successfully!"
        mvn --version
    else
        echo "Failed to install Maven. Please install manually."
        exit 1
    fi
else
    echo "Maven is already installed:"
    mvn --version
fi

echo ""
echo "=== Database Setup ==="
echo "Make sure you have:"
echo "1. SQL Server running"
echo "2. Database 'CMS_system' created"
echo "3. Correct username/password in application.properties"

echo ""
echo "=== Starting Backend ==="
cd backend

# Clean any cached builds
echo "Cleaning previous builds..."
mvn clean

# Compile and run with bypass profile (minimal config)
echo "Starting with bypass profile (no SSL issues)..."
mvn spring-boot:run -Dspring.profiles.active=bypass

echo "Backend startup complete!"