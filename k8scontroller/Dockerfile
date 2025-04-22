FROM golang:1.20-alpine AS builder

WORKDIR /app
COPY go.mod go.sum ./
RUN go mod download

COPY . .
RUN CGO_ENABLED=0 GOOS=linux go build -o controller main.go

FROM alpine:latest
WORKDIR /app
COPY --from=builder /app/controller .

# Create a file for the liveness probe
RUN touch /tmp/healthy

CMD ["./controller"] 