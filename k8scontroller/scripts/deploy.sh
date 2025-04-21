#!/bin/bash

# Exit on error
set -e

echo "🚀 Starting deployment process..."

# Build the controller image
echo "📦 Building controller Docker image..."
docker build -t counter-controller:latest ..

# Apply Kubernetes manifests
echo "📋 Applying Kubernetes manifests..."

# Apply ConfigMap
echo "📝 Applying ConfigMap..."
kubectl apply -f ../k8s/configmap.yaml

# Apply Deployment
echo "📦 Applying Deployment..."
kubectl apply -f ../k8s/deployment.yaml

# Wait for deployment to be ready
echo "⏳ Waiting for deployment to be ready..."
kubectl rollout status deployment/counter-controller -n default

# Show deployment status
echo "📊 Deployment status:"
kubectl get deployments -n default counter-controller

# Show pod status
echo "📊 Pod status:"
kubectl get pods -n default -l app=counter-controller

echo "✅ Deployment completed successfully!"
echo "📝 To check logs, run: kubectl logs -l app=counter-controller -n default"
echo "🔍 To check controller events, run: kubectl get events -n default --field-selector involvedObject.name=counter-controller" 