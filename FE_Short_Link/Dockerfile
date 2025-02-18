
# Sử dụng Node.js để build frontend
FROM node:20.11.1 AS builder

WORKDIR /app

# Copy package.json and package-lock.json
COPY package*.json ./

# Install dependencies
RUN npm install

# Copy the rest of the application code
COPY . .

# Build the application
RUN npm run build

# Verify the build output directory
RUN ls -la /app

# Ensure the build directory exists before copying
RUN if [ -d "/app/.next" ]; then echo "Build directory exists"; else echo "Build directory does not exist"; fi

# Expose the port (if necessary)
EXPOSE 3000

# Command to run the application
CMD ["npm", "start"]

# Giai đoạn chạy app bằng Nginx
FROM nginx:alpine

# Copy build từ step trước vào Nginx
COPY --from=builder /app/.next /usr/share/nginx/html

# Expose cổng 3000
EXPOSE 3000

# Chạy Nginx


CMD ["nginx", "-g", "daemon off;"]
