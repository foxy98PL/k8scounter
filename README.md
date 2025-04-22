# 🚀 Counter Application

A Spring Boot counter service with Kubernetes controller for monitoring nginx deployments.

## 📋 Prerequisites

- Java 21
- Docker
- Minikube
- kubectl
- Gradle
- Go 1.x (for controller)

## 🚀 Quick Start

### 1. Generate TLS Certificate

```bash
# Generate certificate
openssl req -x509 -nodes -days 365 -newkey rsa:2048 \
  -keyout tls.key -out tls.crt \
  -subj "/CN=xcounter.local" \
  -addext "subjectAltName=DNS:xcounter.local"

# Create Kubernetes secret
kubectl create secret tls xcounter-tls \
  --key tls.key --cert tls.crt \
  -n counter
```

### 2. Deploy Application

```bash
# Linux/macOS
./scripts/deploy-counter.sh

# Windows
.\scripts\deploy-counter.ps1
```

### 3. Deploy Controller

```bash
# Linux/macOS
cd k8scontroller/scripts && ./deploy.sh

# Windows
cd k8scontroller\scripts && .\deploy.ps1
```

### 4. Access the Service

1. Get Minikube IP:
   ```bash
   minikube ip
   ```

2. Add to hosts file:
   ```bash
   # Linux/macOS
   sudo echo "<minikube-ip> xcounter.local" >> /etc/hosts
   
   # Windows (Run as Administrator)
   # Open PowerShell and run:
   Add-Content -Path "C:\Windows\System32\drivers\etc\hosts" -Value "`n<minikube-ip> xcounter.local"
   
   # Or manually edit:
   # 1. Open Notepad as Administrator
   # 2. Open C:\Windows\System32\drivers\etc\hosts
   # 3. Add line: <minikube-ip> xcounter.local
   # 4. Save the file
   ```

3. Access via HTTPS:
   ```bash
   # Using hostname (recommended)
   curl -k https://xcounter.local/api/counter/value

   # Or using IP with host header
   curl -k https://<minikube-ip>/api/counter/value -H "Host: xcounter.local"
   ```

### 5. Test with Nginx

To test the controller functionality, deploy and remove nginx using the provided YAMLs:

```bash
# Deploy nginx (choose one of the YAMLs)
kubectl apply -f k8scontroller/k8s/nginx.yaml
# or
kubectl apply -f k8scontroller/k8s/nginx2.yaml
# or
kubectl apply -f k8scontroller/k8s/nginx3.yaml

# Check counter value
curl -k https://xcounter.local/api/counter/value

# Remove nginx
kubectl delete -f k8scontroller/k8s/nginx.yaml
# or
kubectl delete -f k8scontroller/k8s/nginx2.yaml
# or
kubectl delete -f k8scontroller/k8s/nginx3.yaml

# Check counter value again
curl -k https://xcounter.local/api/counter/value
```

## 🧹 Cleanup

### Application
```bash
# Linux/macOS
./scripts/cleanup-counter.sh

# Windows
.\scripts\cleanup-counter.ps1
```

### Controller
```bash
# Linux/macOS
cd k8scontroller/scripts && ./cleanup.sh

# Windows
cd k8scontroller\scripts && .\cleanup.ps1
```

## 📄 License

MIT