@echo off
echo ================================================
echo Setting up CMS_System Database for SQL Server
echo ================================================

echo Connecting to SQL Server and running setup script...
sqlcmd -S localhost,1433 -U sa -P 12345 -i database-setup.sql

if %ERRORLEVEL% EQU 0 (
    echo.
    echo ✅ Database setup completed successfully!
    echo ✅ CMS_System database is ready for Spring Boot application
    echo.
    echo Next steps:
    echo 1. Start the Spring Boot application: ./mvnw spring-boot:run
    echo 2. Access the application: http://localhost:8080
    echo 3. View API documentation: http://localhost:8080/swagger-ui.html
) else (
    echo.
    echo ❌ Database setup failed!
    echo Please check:
    echo 1. SQL Server is running on localhost:1433
    echo 2. Username 'sa' and password '12345' are correct
    echo 3. You have permission to create databases
)

pause