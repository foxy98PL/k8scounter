# PowerShell script to prepare Go dependencies
Write-Host "Preparing Go dependencies..." -ForegroundColor Yellow

# Change to the project directory
Set-Location ..

# Initialize Go modules if not already done
if (-not (Test-Path "go.mod")) {
    Write-Host "Initializing Go modules..." -ForegroundColor Yellow
    go mod init counter-controller
}

# Update and download dependencies
Write-Host "Updating dependencies..." -ForegroundColor Yellow
go mod tidy
go mod download

# Verify go.mod and go.sum exist
if (-not (Test-Path "go.mod")) {
    Write-Host "Error: go.mod file was not created. Please check for errors above." -ForegroundColor Red
    exit 1
}

if (-not (Test-Path "go.sum")) {
    Write-Host "Error: go.sum file was not created. Please check for errors above." -ForegroundColor Red
    exit 1
}

Write-Host "Dependencies prepared successfully!" -ForegroundColor Green
Write-Host "You can now run the deployment script." -ForegroundColor Cyan 