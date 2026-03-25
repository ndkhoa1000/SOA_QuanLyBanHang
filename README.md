# Sales Management System (SOA)

This project is a microservices-based sales management system split into three containerized services.

## Architecture

- **KhachHang Service**: Handles customer data.
- **HangHoa Service**: Handles product data.
- **HoaDon Service**: Handles invoices and provides the Web UI (JSPs/Servlets).

## Prerequisites

- Java 8
- Maven 3.x
- Docker and Docker Compose

## Build and Run

### 1. Build the artifacts
The project uses Maven profiles to package different parts of the application:
```bash
mvn clean package -Pkhachhang
mvn package -Phanghoa
mvn package -Phoadon
```

### 2. Run with Docker Compose
To start the services with automatic rebuild on file changes (Hot Reload):
```bash
docker compose watch
```

This command will:
1. Build and start all services.
2. Watch for changes in `src/`, `WebContent/`, or `pom.xml`.
3. Automatically rebuild and redeploy the affected container when you save a file.

Alternatively, to just run without watching:
```bash
docker compose up --build -d
```

### 3. Access the services
- **Web UI & Invoice Service**: [http://localhost:8080](http://localhost:8080)
- **Customer API**: [http://localhost:8082/rest/khachhang/all](http://localhost:8082/rest/khachhang/all)
- **Product API**: [http://localhost:8083/rest/hanghoa/all](http://localhost:8083/rest/hanghoa/all)

### 4. run test

```java
mvn exec:java -Dexec.mainClass="service.client.TestServiceClient" \
  -DHOA_DON_BASE_URL="http://localhost:8080/rest" \
  -DKHACH_HANG_BASE_URL="http://localhost:8082/rest" \
  -DHANG_HOA_BASE_URL="http://localhost:8083/rest"
```

## Service Discovery
Endpoints are configured via environment variables in `docker-compose.yml`:
- `KHACH_HANG_BASE_URL`
- `HANG_HOA_BASE_URL`
- `HOA_DON_BASE_URL`
