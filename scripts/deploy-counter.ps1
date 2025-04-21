# PowerShell deployment script for counter app only
Write-Host "Starting Minikube deployment for counter app..."

# Change to root directory
$rootDir = Split-Path -Parent $PSScriptRoot
Set-Location $rootDir

# Start Minikube if not running
$minikubeStatus = minikube status | Select-String "Running" -Quiet
if (-not $minikubeStatus) {
    Write-Host "Starting Minikube..."
    minikube start
}

# Set up Docker environment
Write-Host "Setting up Docker environment..."
minikube docker-env | Invoke-Expression

# Build Docker image
Write-Host "Building Docker image..."
docker build -t counter-app:latest .

# Create namespace if it doesn't exist
$namespaceExists = kubectl get namespace counter -o name 2>$null
if (-not $namespaceExists) {
    Write-Host "Creating counter namespace..."
    kubectl create namespace counter
}

# Deploy Kubernetes resources
Write-Host "Deploying Kubernetes resources..."
kubectl apply -f k8s/deployment.yaml -n counter
kubectl apply -f k8s/service.yaml -n counter
kubectl apply -f k8s/ingress.yaml -n counter

# Enable Ingress addon
Write-Host "Enabling Ingress addon..."
minikube addons enable ingress

# Wait for pods to be ready
Write-Host "Waiting for pods to be ready..."
Start-Sleep -Seconds 10

# Get Minikube IP
$minikubeIP = minikube ip
Write-Host "Minikube IP: $minikubeIP"

# Check deployment status
Write-Host "Checking deployment status..."
kubectl get all -n counter

Write-Host "Deployment complete! You can access the application at:"
Write-Host "GET endpoint: http://$minikubeIP/api/counter/value"
Write-Host "To test the counter:"
Write-Host "1. Check the value: Invoke-RestMethod -Uri 'http://$minikubeIP/api/counter/value' -Method Get"
Write-Host "2. Monitor logs: kubectl logs -n counter deployment/counter-app -f" 