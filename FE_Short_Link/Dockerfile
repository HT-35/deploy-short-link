# # Sử dụng hình ảnh Node.js làm hình ảnh nguồn
# FROM node:20.11.1 AS builder

# # Thiết lập thư mục làm việc
# WORKDIR /usr/src/app

# # Sao chép package.json và package-lock.json
# COPY package*.json ./

# # Cài đặt các phụ thuộc
# RUN npm install

# # Sao chép mã nguồn vào hình ảnh
# COPY . .

# # Mở cổng mà ứng dụng sẽ chạy
# EXPOSE 3000

# # Lệnh để chạy ứng dụng
# CMD ["npm", "start"]



# Sử dụng Node.js
FROM node:20.11.1 AS builder

# Thiết lập thư mục làm việc
WORKDIR /usr/src/app

# Copy package.json và package-lock.json
COPY package*.json ./

# Cài đặt dependencies
RUN npm install

# Sao chép toàn bộ source code vào container
COPY . .

# Build production
RUN npm run build

# Dùng image Node.js nhỏ hơn để chạy ứng dụng
FROM node:20.11.1

WORKDIR /usr/src/app

# Copy build từ bước trước
COPY --from=builder /usr/src/app .

# Chạy ứng dụng production
CMD ["npm", "run", "start"]
