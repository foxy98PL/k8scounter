#!/bin/bash

echo "Starting Minikube deployment for counter app..."

# Start Minikube if not running
if ! minikube status | grep -q "Running"; then
    echo "Starting Minikube..."
    minikube start
fi

# Set up Docker environment
echo "Setting up Docker environment..."
eval $(minikube docker-env)

# Build Docker image
echo "Building Docker image..."
docker build -t counter-app:latest .

# Create namespace if it doesn't exist
if ! kubectl get namespace counter &> /dev/null; then
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
MINIKUBE_IP=$(minikube ip)
echo "Minikube IP: $MINIKUBE_IP"

# Check deployment status
echo "Checking deployment status..."
kubectl get all -n counter

echo "Deployment complete! You can access the application at:"
echo "GET endpoint: http://$MINIKUBE_IP/api/counter/value"
echo "To test the counter:"
echo "1. Check the value: curl http://$MINIKUBE_IP/api/counter/value"
echo "2. Monitor logs: kubectl logs -n counter deployment/counter-app -f" 