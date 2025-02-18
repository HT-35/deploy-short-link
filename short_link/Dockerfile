# Sử dụng multi-stage build để giảm kích thước image
FROM eclipse-temurin:17-jdk AS builder

# Đặt thư mục làm việc
WORKDIR /app

# Copy toàn bộ source code
COPY . .

# Cấp quyền thực thi cho gradlew
RUN chmod +x ./gradlew

# Build ứng dụng với Gradle wrapper
RUN ./gradlew build -x test --no-daemon

# Stage 2: Runtime
FROM eclipse-temurin:17-jre

WORKDIR /app

# Copy JAR từ stage builder
COPY --from=builder /app/build/libs/*.jar app.jar

# Copy file .env
COPY .env .

# Expose port
EXPOSE 4000

# Chạy ứng dụng
ENTRYPOINT ["java", "-jar", "app.jar"]
