# Sử dụng image của OpenJDK 17
FROM openjdk:17-jdk-slim

# Đặt thư mục làm việc trong container
WORKDIR /app

# Copy file JAR vào container
COPY build/libs/short_link-0.0.1-SNAPSHOT.jar app.jar

# Expose cổng ứng dụng
EXPOSE 4000
# Copy environment file
COPY .env /app/.env

# Chạy ứng dụng
ENTRYPOINT ["java", "-jar", "app.jar"]
