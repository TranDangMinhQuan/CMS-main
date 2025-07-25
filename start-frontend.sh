#!/bin/bash

echo "Starting Badminton Booking Frontend..."

cd frontend

# Check if npm is available
if ! command -v npm &> /dev/null; then
    echo "Node.js/npm is not installed. Please install Node.js first."
    exit 1
fi

# Install dependencies if node_modules doesn't exist
if [ ! -d "node_modules" ]; then
    echo "Installing dependencies..."
    npm install
fi

# Start the React development server
echo "Starting React development server..."
npm run dev || npm start

echo "Frontend started successfully!"