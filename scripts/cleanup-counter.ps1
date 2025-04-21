# PowerShell cleanup script for counter app
Write-Host "Starting cleanup..."

# Delete namespace and all resources
Write-Host "Deleting counter namespace and all resources..."
kubectl delete namespace counter

# Remove Docker image
Write-Host "Removing Docker image..."
docker rmi counter-app:latest

Write-Host "Cleanup complete!" 