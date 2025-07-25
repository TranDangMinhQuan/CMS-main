-- ================================================
-- Script để tạo lại Database cho Badminton Court Booking System
-- ================================================

-- Đóng tất cả kết nối đến database (nếu có)
USE master;
GO

-- Ngắt kết nối tất cả session đang dùng database
IF EXISTS (SELECT name FROM sys.databases WHERE name = 'CMS_System')
BEGIN
    ALTER DATABASE CMS_System SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
    DROP DATABASE CMS_System;
END
GO

-- Tạo database mới
CREATE DATABASE CMS_System
COLLATE SQL_Latin1_General_CP1_CI_AS;
GO

-- Sử dụng database vừa tạo
USE CMS_System;
GO

-- Cấu hình database
ALTER DATABASE CMS_System SET RECOVERY SIMPLE;
GO

PRINT 'Database CMS_System đã được tạo thành công!';
PRINT 'Spring Boot sẽ tự động tạo các bảng khi khởi động.';
GO