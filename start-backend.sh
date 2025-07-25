#!/bin/bash

echo "Starting Badminton Booking Backend..."

cd backend

# Check if mvn is available
if ! command -v mvn &> /dev/null; then
    echo "Maven is not installed. Please install Maven first."
    exit 1
fi

# Check for profile argument
PROFILE=${1:-default}

case $PROFILE in
    "dev")
        echo "Starting with DEV profile (SSL alternative config)..."
        mvn spring-boot:run -Dspring.profiles.active=dev
        ;;
    "nossl")
        echo "Starting with NO-SSL profile (complete SSL bypass)..."
        mvn spring-boot:run -Dspring.profiles.active=nossl
        ;;
    "bypass")
        echo "Starting with BYPASS profile (minimal config)..."
        mvn spring-boot:run -Dspring.profiles.active=bypass
        ;;
    *)
        echo "Starting with DEFAULT profile (trust SSL certificates)..."
        echo "If SSL errors occur, try: ./start-backend.sh dev|nossl|bypass"
        mvn spring-boot:run
        ;;
esac

echo "Backend startup complete!"