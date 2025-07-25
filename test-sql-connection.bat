@echo off
echo 🔍 Test kết nối SQL Server cho database CMS_system...
echo.

echo 📋 Các tùy chọn kết nối:
echo.
echo 1. Default Instance (localhost:1433)
echo 2. SQL Server Express (localhost\SQLEXPRESS)  
echo 3. Named Instance (localhost\MSSQLSERVER)
echo 4. Custom Host/Port
echo 5. Windows Authentication
echo.

set /p choice="Chọn tùy chọn (1-5): "

if "%choice%"=="1" (
    echo.
    echo 🧪 Test với Default Instance...
    echo Connection String: jdbc:sqlserver://localhost:1433;databaseName=CMS_system
    echo Username: sa
    echo Password: [hidden]
    echo.
    echo Updating application.properties...
    cd backend\src\main\resources
    (
        echo # Database Configuration - Default Instance
        echo spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=CMS_system;encrypt=false;trustServerCertificate=true
        echo spring.datasource.username=sa
        echo spring.datasource.password=Password123
        echo spring.datasource.driver-class-name=com.microsoft.sqlserver.jdbc.SQLServerDriver
        echo.
        echo # JPA Configuration
        echo spring.jpa.hibernate.ddl-auto=update
        echo spring.jpa.database-platform=org.hibernate.dialect.SQLServerDialect
        echo spring.jpa.show-sql=true
        echo spring.jpa.properties.hibernate.format_sql=true
        echo.
        echo # JWT Configuration
        echo jwt.secret=mySecretKey
        echo jwt.expiration=86400000
        echo.
        echo # Server Configuration
        echo server.port=8080
        echo.
        echo # CORS Configuration
        echo spring.web.cors.allowed-origins=http://localhost:3000
        echo spring.web.cors.allowed-methods=GET,POST,PUT,DELETE,OPTIONS
        echo spring.web.cors.allowed-headers=*
        echo spring.web.cors.allow-credentials=true
    ) > application.properties
    cd ..\..\..\..
    
) else if "%choice%"=="2" (
    echo.
    echo 🧪 Test với SQL Server Express...
    echo Connection String: jdbc:sqlserver://localhost\SQLEXPRESS;databaseName=CMS_system
    echo.
    cd backend\src\main\resources
    (
        echo # Database Configuration - SQL Server Express
        echo spring.datasource.url=jdbc:sqlserver://localhost\\SQLEXPRESS;databaseName=CMS_system;encrypt=false;trustServerCertificate=true
        echo spring.datasource.username=sa
        echo spring.datasource.password=Password123
        echo spring.datasource.driver-class-name=com.microsoft.sqlserver.jdbc.SQLServerDriver
        echo.
        echo # JPA Configuration
        echo spring.jpa.hibernate.ddl-auto=update
        echo spring.jpa.database-platform=org.hibernate.dialect.SQLServerDialect
        echo spring.jpa.show-sql=true
        echo spring.jpa.properties.hibernate.format_sql=true
        echo.
        echo # JWT Configuration
        echo jwt.secret=mySecretKey
        echo jwt.expiration=86400000
        echo.
        echo # Server Configuration
        echo server.port=8080
        echo.
        echo # CORS Configuration
        echo spring.web.cors.allowed-origins=http://localhost:3000
        echo spring.web.cors.allowed-methods=GET,POST,PUT,DELETE,OPTIONS
        echo spring.web.cors.allowed-headers=*
        echo spring.web.cors.allow-credentials=true
    ) > application.properties
    cd ..\..\..\..
    
) else if "%choice%"=="3" (
    echo.
    echo 🧪 Test với Named Instance MSSQLSERVER...
    echo Connection String: jdbc:sqlserver://localhost\MSSQLSERVER;databaseName=CMS_system
    echo.
    cd backend\src\main\resources
    (
        echo # Database Configuration - Named Instance
        echo spring.datasource.url=jdbc:sqlserver://localhost\\MSSQLSERVER;databaseName=CMS_system;encrypt=false;trustServerCertificate=true
        echo spring.datasource.username=sa
        echo spring.datasource.password=Password123
        echo spring.datasource.driver-class-name=com.microsoft.sqlserver.jdbc.SQLServerDriver
        echo.
        echo # JPA Configuration
        echo spring.jpa.hibernate.ddl-auto=update
        echo spring.jpa.database-platform=org.hibernate.dialect.SQLServerDialect
        echo spring.jpa.show-sql=true
        echo spring.jpa.properties.hibernate.format_sql=true
        echo.
        echo # JWT Configuration
        echo jwt.secret=mySecretKey
        echo jwt.expiration=86400000
        echo.
        echo # Server Configuration
        echo server.port=8080
        echo.
        echo # CORS Configuration
        echo spring.web.cors.allowed-origins=http://localhost:3000
        echo spring.web.cors.allowed-methods=GET,POST,PUT,DELETE,OPTIONS
        echo spring.web.cors.allowed-headers=*
        echo spring.web.cors.allow-credentials=true
    ) > application.properties
    cd ..\..\..\..
    
) else if "%choice%"=="4" (
    echo.
    set /p host="Nhập host (VD: 192.168.1.100): "
    set /p port="Nhập port (VD: 1433): "
    echo.
    echo 🧪 Test với Custom %host%:%port%...
    cd backend\src\main\resources
    (
        echo # Database Configuration - Custom Host/Port
        echo spring.datasource.url=jdbc:sqlserver://%host%:%port%;databaseName=CMS_system;encrypt=false;trustServerCertificate=true
        echo spring.datasource.username=sa
        echo spring.datasource.password=Password123
        echo spring.datasource.driver-class-name=com.microsoft.sqlserver.jdbc.SQLServerDriver
        echo.
        echo # JPA Configuration
        echo spring.jpa.hibernate.ddl-auto=update
        echo spring.jpa.database-platform=org.hibernate.dialect.SQLServerDialect
        echo spring.jpa.show-sql=true
        echo spring.jpa.properties.hibernate.format_sql=true
        echo.
        echo # JWT Configuration
        echo jwt.secret=mySecretKey
        echo jwt.expiration=86400000
        echo.
        echo # Server Configuration
        echo server.port=8080
        echo.
        echo # CORS Configuration
        echo spring.web.cors.allowed-origins=http://localhost:3000
        echo spring.web.cors.allowed-methods=GET,POST,PUT,DELETE,OPTIONS
        echo spring.web.cors.allowed-headers=*
        echo spring.web.cors.allow-credentials=true
    ) > application.properties
    cd ..\..\..\..
    
) else if "%choice%"=="5" (
    echo.
    echo 🧪 Test với Windows Authentication...
    echo Connection String: jdbc:sqlserver://localhost:1433;databaseName=CMS_system;integratedSecurity=true
    echo.
    cd backend\src\main\resources
    (
        echo # Database Configuration - Windows Authentication
        echo spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=CMS_system;encrypt=false;trustServerCertificate=true;integratedSecurity=true
        echo spring.datasource.username=
        echo spring.datasource.password=
        echo spring.datasource.driver-class-name=com.microsoft.sqlserver.jdbc.SQLServerDriver
        echo.
        echo # JPA Configuration
        echo spring.jpa.hibernate.ddl-auto=update
        echo spring.jpa.database-platform=org.hibernate.dialect.SQLServerDialect
        echo spring.jpa.show-sql=true
        echo spring.jpa.properties.hibernate.format_sql=true
        echo.
        echo # JWT Configuration
        echo jwt.secret=mySecretKey
        echo jwt.expiration=86400000
        echo.
        echo # Server Configuration
        echo server.port=8080
        echo.
        echo # CORS Configuration
        echo spring.web.cors.allowed-origins=http://localhost:3000
        echo spring.web.cors.allowed-methods=GET,POST,PUT,DELETE,OPTIONS
        echo spring.web.cors.allowed-headers=*
        echo spring.web.cors.allow-credentials=true
    ) > application.properties
    cd ..\..\..\..
    
) else (
    echo ❌ Lựa chọn không hợp lệ!
    pause
    exit /b 1
)

echo.
echo ✅ Đã cập nhật application.properties
echo.
echo 🚀 Chạy test Spring Boot...
cd backend
mvnw.cmd clean spring-boot:run

pause