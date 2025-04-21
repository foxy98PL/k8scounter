#!/bin/bash

echo "Starting Minikube deployment for counter app..."

# Change to the root directory of the script
rootDir="$(dirname "$0")"
cd "$rootDir"

# Start Minikube if not running
minikubeStatus=$(minikube status | grep -q "Running" && echo "running" || echo "not running")
if [ "$minikubeStatus" != "running" ]; then
    echo "Starting Minikube..."
    minikube start
fi

# Set up Docker environment
echo "Setting up Docker environment..."
eval $(minikube -p minikube docker-env)

# Build Docker image
echo "Building Docker image..."
docker build -t counter-app:latest .

# Create namespace if it doesn't exist
namespaceExists=$(kubectl get namespace counter --ignore-not-found)
if [ -z "$namespaceExists" ]; then
    echo "Creating counter namespace..."
    kubectl create namespace counter
fi

# Deploy Kubernetes resources
echo "Deploying Kubernetes resources..."
kubectl apply -f k8s/deployment.yaml -n counter
kubectl apply -f k8s/service.yaml -n counter
kubectl apply -f k8s/ingress.yaml -n counter

# Enable Ingress addon
echo "Enabling Ingress addon..."
minikube addons enable ingress

# Wait for pods to be ready
echo "Waiting for pods to be ready..."
sleep 10

# Get Minikube IP
minikubeIP=$(minikube ip)
echo "Minikube IP: $minikubeIP"

# Check deployment status
echo "Checking deployment status..."
kubectl get all -n counter

echo "Deployment complete! You can access the application at:"
echo "GET endpoint: http://$minikubeIP/api/counter/value"
echo "To test the counter:"
echo "1. Check the value: curl http://$minikubeIP/api/counter/value"
echo "2. Monitor logs: kubectl logs -n counter deployment/counter-app -f"
