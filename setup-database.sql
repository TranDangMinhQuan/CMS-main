-- SQL Script to create CMS_system database
-- Run this in SQL Server Management Studio or sqlcmd before starting the application

-- Create database if not exists
IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = 'CMS_system')
BEGIN
    CREATE DATABASE CMS_system;
    PRINT 'Database CMS_system created successfully';
END
ELSE
BEGIN
    PRINT 'Database CMS_system already exists';
END

-- Use the database
USE CMS_system;

-- Verify database is ready
SELECT 'Database CMS_system is ready for use' AS Status;