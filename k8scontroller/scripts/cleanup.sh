#!/bin/bash

echo "Starting cleanup process..."

# Delete deployment
echo "Deleting deployment..."
kubectl delete deployment counter-controller -n default

# Delete ConfigMap
echo "Deleting ConfigMap..."
kubectl delete configmap controller-config -n default

# Delete RBAC
echo "Deleting RBAC configuration..."
kubectl delete -f ../k8s/rbac.yaml --ignore-not-found

# Show remaining resources
echo "Remaining resources:"
kubectl get all -n default

echo "Cleanup completed successfully!"
