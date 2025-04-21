#!/bin/bash

# Exit on error
set -e

echo "🧹 Starting cleanup process..."

# Delete deployment
echo "🗑️ Deleting deployment..."
kubectl delete deployment counter-controller -n default

# Delete ConfigMap
echo "🗑️ Deleting ConfigMap..."
kubectl delete configmap controller-config -n default

# Show remaining resources
echo "📊 Remaining resources:"
kubectl get all -n default

echo "✅ Cleanup completed successfully!" 