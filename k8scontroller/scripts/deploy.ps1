# PowerShell deployment script
Write-Host "Starting deployment process..." -ForegroundColor Green

# Build the controller image
Write-Host "Building controller Docker image..." -ForegroundColor Yellow
go mod tidy
go mod download
docker build -t counter-controller:latest ..

# Apply Kubernetes manifests
Write-Host "Applying Kubernetes manifests..." -ForegroundColor Yellow

# Apply RBAC
Write-Host "Applying RBAC configuration..." -ForegroundColor Yellow
kubectl apply -f ../k8s/rbac.yaml

# Apply ConfigMap
Write-Host "Applying ConfigMap..." -ForegroundColor Yellow
kubectl apply -f ../k8s/configmap.yaml

# Apply Deployment
Write-Host "Applying Deployment..." -ForegroundColor Yellow
kubectl apply -f ../k8s/deployment.yaml

# Wait for deployment to be ready
Write-Host "Waiting for deployment to be ready..." -ForegroundColor Yellow
kubectl rollout status deployment/counter-controller -n counter

# Show deployment status
Write-Host "Deployment status:" -ForegroundColor Yellow
kubectl get deployments -n counter counter-controller

# Show pod status
Write-Host "Pod status:" -ForegroundColor Yellow
kubectl get pods -n counter -l app=counter-controller

Write-Host "Deployment completed successfully!" -ForegroundColor Green
Write-Host "To check logs, run: kubectl logs -l app=counter-controller -n counter" -ForegroundColor Cyan
Write-Host "To check controller events, run: kubectl get events -n counter --field-selector involvedObject.name=counter-controller" -ForegroundColor Cyan
