#!/bin/bash

echo "Starting cleanup..."

# Delete namespace and all resources
echo "Deleting counter namespace and all resources..."
kubectl delete namespace counter

# Remove Docker image
echo "Removing Docker image..."
docker rmi counter-app:latest

echo "Cleanup complete!" 