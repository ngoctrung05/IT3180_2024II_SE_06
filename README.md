# IT3180_2024II_SE_06
# Hướng dẫn chạy dự án 
## 1. Clone dự án về máy
```bash
git clone https://github.com/ngoctrung05hd/IT3180_2024II_SE_06.git
cd IT3180_2024II_SE_06
```
- Nếu bạn sử dụng IDE chuyên dụng như Eclipse hoặc InteliJ, chỉ cần clone dự án về và chạy file IT3180Application.java trong main\java\com\IT3180. 
- Nếu không thì hãy thực hiện các bước tiếp
## 2. Cài đặt các công cụ cần thiết
Trước khi chạy dự án, hãy đảm bảo bạn đã cài đặt:
- **Java JDK** 
- **Maven** 
## 3. Cài đặt dependencies với Maven
```bash
mvn clean install
```
## 4. Chạy ứng dụng Spring Boot

```bash
mvn spring-boot:run
```
## 5. Kiểm tra ứng dụng
Truy cập:
```
http://localhost:8080/login
```
