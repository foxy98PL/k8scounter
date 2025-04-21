# PowerShell cleanup script
Write-Host "Starting cleanup process..." -ForegroundColor Green

# Delete deployment
Write-Host "Deleting deployment..." -ForegroundColor Yellow
kubectl delete deployment counter-controller -n default

# Delete ConfigMap
Write-Host "Deleting ConfigMap..." -ForegroundColor Yellow
kubectl delete configmap controller-config -n default

# Delete RBAC
Write-Host "Deleting RBAC configuration..." -ForegroundColor Yellow
kubectl delete -f ../k8s/rbac.yaml --ignore-not-found

# Show remaining resources
Write-Host "Remaining resources:" -ForegroundColor Yellow
kubectl get all -n default

Write-Host "Cleanup completed successfully!" -ForegroundColor Green 