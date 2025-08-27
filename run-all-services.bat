@echo off
cd /d C:\Users\mslal\koshflow

start cmd /k "cd account-service && mvnw spring-boot:run"
start cmd /k "cd elasticsearch-service && mvnw spring-boot:run"
start cmd /k "cd gateway-service && mvnw spring-boot:run"
start cmd /k "cd notification-service && mvnw spring-boot:run"
start cmd /k "cd registry-service && mvnw spring-boot:run"
start cmd /k "cd transaction-service && mvnw spring-boot:run"
start cmd /k "cd user-service && mvnw spring-boot:run"