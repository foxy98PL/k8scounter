#!/bin/bash

# Exit on error
set -e

echo "Preparing Go dependencies..."

# Change to the project directory
cd ..

# Initialize Go modules if not already done
if [ ! -f "go.mod" ]; then
    echo "Initializing Go modules..."
    go mod init counter-controller
fi

# Download dependencies and create go.sum
echo "Downloading dependencies..."
go mod tidy
go mod download

# Verify go.sum exists
if [ ! -f "go.sum" ]; then
    echo "Error: go.sum file was not created. Please check for errors above."
    exit 1
fi

echo "Dependencies prepared successfully!"
echo "You can now run the deployment script."