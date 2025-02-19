
# # Sử dụng Node.js
# FROM node:20.11.1 AS builder

# # Thiết lập thư mục làm việc
# WORKDIR /usr/src/app

# # Copy package.json và package-lock.json
# COPY package*.json ./

# # Cài đặt dependencies
# RUN npm install

# # Sao chép toàn bộ source code vào container
# COPY . .

# # Build production
# RUN npm run build

# # Dùng image Node.js nhỏ hơn để chạy ứng dụng
# FROM node:20.11.1

# WORKDIR /usr/src/app

# # Copy build từ bước trước
# COPY --from=builder /usr/src/app .

# # Chạy ứng dụng production
# # CMD ["npm", "run", "start"]

# ENTRYPOINT ["sh", "-c", "npm run build && npm run start"]

# =============================================================================================
# Sử dụng Node.js 20
FROM node:20.11.1 AS runner

# Thiết lập thư mục làm việc
WORKDIR /usr/src/app

# Copy package.json và package-lock.json
COPY package*.json ./

# Cài đặt dependencies
RUN npm install

# Sao chép toàn bộ source code
COPY . .

# Cấu hình để build lại mỗi lần container chạy
ENTRYPOINT ["sh", "-c", "npm install && npm run build && npm run start"]
