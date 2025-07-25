# 🏸 Badminton Court Booking System

A comprehensive badminton court booking management system built with Spring Boot (backend) and React TypeScript (frontend).

## Features

### For All Users (Guest)
- View available courts and their information
- Browse court reviews and ratings
- View pricing information

### For Members
- All guest features
- User registration and login
- Book courts online
- Choose payment method (online/cash at court)
- Rent rackets (30,000 VND each)
- Apply discount coupons
- View and manage personal bookings
- Write reviews for courts

### For Staff
- All member features
- View dashboard with daily statistics
- Manage court bookings
- Process payments for walk-in customers
- Manage racket rentals
- View revenue reports

### For Owner
- All staff features
- Manage users (create staff, suspend/activate users)
- Manage courts (set status, availability)
- View comprehensive reports
- Monthly revenue analysis
- Transaction history

## Technology Stack

### Backend
- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Security** (JWT Authentication)
- **Spring Data JPA**
- **Microsoft SQL Server** (Database)
- **Maven** (Build tool)

### Frontend
- **React 18** with TypeScript
- **React Router** (Navigation)
- **Axios** (HTTP Client)
- **CSS Modules** (Styling)

## Project Structure

```
badminton-booking-system/
├── backend/                    # Spring Boot backend
│   ├── src/main/java/com/badminton/booking/
│   │   ├── controller/        # REST Controllers
│   │   ├── service/           # Service Interfaces
│   │   ├── serviceimpl/       # Service Implementations
│   │   ├── repository/        # JPA Repositories
│   │   ├── entity/            # JPA Entities
│   │   ├── dto/               # Data Transfer Objects
│   │   ├── enums/             # Enumerations
│   │   └── config/            # Security & JWT Config
│   ├── src/main/resources/
│   │   └── application.properties
│   └── pom.xml
├── frontend/                   # React frontend
│   ├── src/
│   │   ├── components/        # Reusable components
│   │   ├── pages/             # Page components
│   │   ├── services/          # API services
│   │   ├── context/           # React contexts
│   │   ├── types/             # TypeScript types
│   │   └── utils/             # Utility functions
│   ├── public/
│   └── package.json
├── start-backend.sh           # Backend startup script
├── start-frontend.sh          # Frontend startup script
└── README.md
```

## Database Schema

The system uses the following main entities:
- **Users** (id, username, email, fullName, phoneNumber, role, isActive, timestamps)
- **Courts** (id, name, description, hourlyRate, status, timestamps)
- **Bookings** (id, userId, courtId, startTime, endTime, totalAmount, status, notes, timestamps)
- **Payments** (id, bookingId, amount, paymentMethod, status, transactionId, paidAt, timestamps)
- **Reviews** (id, userId, courtId, rating, comment, createdAt)
- **Coupons** (id, code, description, discountPercentage, maxDiscountAmount, validity, usageLimit, timestamps)
- **RacketRentals** (id, bookingId, quantity, pricePerRacket, totalAmount, timestamps)

## Prerequisites

- **Java 17** or higher
- **Maven 3.6+**
- **Node.js 16+** and npm
- **Microsoft SQL Server** (or configure for another database)

## Installation & Setup

### Database Setup

1. Install Microsoft SQL Server
2. Create a database named `badminton_booking`
3. Update the database configuration in `backend/src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=badminton_booking;encrypt=false;trustServerCertificate=true
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### Backend Setup

1. Navigate to the backend directory:
```bash
cd backend
```

2. Install dependencies and run the application:
```bash
mvn clean install
mvn spring-boot:run
```

Or use the provided script:
```bash
./start-backend.sh
```

The backend will start on `http://localhost:8080`

### Frontend Setup

1. Navigate to the frontend directory:
```bash
cd frontend
```

2. Install dependencies:
```bash
npm install
```

3. Start the development server:
```bash
npm run dev
```

Or use the provided script:
```bash
./start-frontend.sh
```

The frontend will start on `http://localhost:3000`

## API Documentation

### Authentication Endpoints
- `POST /api/auth/login` - User login
- `POST /api/auth/register` - User registration

### Court Endpoints
- `GET /api/courts` - Get all courts (public)
- `GET /api/courts/{id}` - Get court by ID (public)
- `GET /api/courts/available` - Get available courts (public)
- `POST /api/courts` - Create court (Owner only)
- `PUT /api/courts/{id}` - Update court (Owner only)
- `PUT /api/courts/{id}/status` - Update court status (Staff/Owner)

### Booking Endpoints
- `POST /api/bookings` - Create booking (Members+)
- `GET /api/bookings/my-bookings` - Get user bookings (Members+)
- `GET /api/bookings` - Get all bookings (Staff+)
- `PUT /api/bookings/{id}/status` - Update booking status (Staff+)

### Review Endpoints
- `POST /api/reviews` - Create review (Members+)
- `GET /api/reviews/court/{courtId}` - Get court reviews (public)
- `GET /api/reviews/court/{courtId}/average` - Get average rating (public)

### Admin Endpoints
- `GET /api/users` - Get all users (Owner only)
- `POST /api/users/staff` - Create staff (Owner only)
- `PUT /api/users/{id}/status` - Update user status (Owner only)

### Reports Endpoints
- `GET /api/reports/revenue/today` - Today's revenue (Staff+)
- `GET /api/reports/revenue/monthly` - Monthly revenue (Staff+)
- `GET /api/reports/dashboard` - Dashboard data (Staff+)

## User Roles & Permissions

### Guest
- View courts and reviews
- No booking capabilities

### Member
- All guest features
- Book courts
- Manage bookings
- Write reviews
- Apply coupons

### Staff
- All member features
- View dashboard
- Manage all bookings
- Process payments
- View reports

### Owner
- All staff features
- User management
- Court management
- Full administrative access

## Default Test Data

When the application starts, it will create default users for testing:

- **Owner**: username: `owner`, password: `password123`
- **Staff**: username: `staff`, password: `password123`
- **Member**: username: `member`, password: `password123`

## Configuration

### Backend Configuration (application.properties)

```properties
# Database Configuration
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=badminton_booking
spring.datasource.username=sa
spring.datasource.password=your_password

# JPA Configuration
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true

# Server Configuration
server.port=8080
server.servlet.context-path=/api

# JWT Configuration
jwt.secret=mySecretKey
jwt.expiration=86400000

# CORS Configuration
cors.allowed-origins=http://localhost:3000
```

### Frontend Configuration

The frontend is configured to connect to the backend at `http://localhost:8080/api`. Update the `API_BASE_URL` in `src/services/api.ts` if needed.

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

## License

This project is licensed under the MIT License.

## Troubleshooting

### SQL Server SSL Connection Issues

If you encounter SSL/TLS certificate errors like:
```
"encrypt" property is set to "true" and "trustServerCertificate" property is set to "false"
```

**Solution 1:** Update `application.properties` with:
```properties
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=badminton_booking;encrypt=false;trustServerCertificate=true;integratedSecurity=false;authenticationScheme=nativeAuthentication
```

**Solution 2:** Use the alternative development profile:
```bash
mvn spring-boot:run -Dspring.profiles.active=dev
```

**Solution 3:** For older SQL Server versions, try:
```properties
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=badminton_booking;trustServerCertificate=true
```

### Common Issues

- **Database not found**: Make sure SQL Server is running and database `badminton_booking` exists
- **Port conflicts**: Change `server.port` in `application.properties` if port 8080 is in use
- **Authentication failed**: Verify SQL Server username/password in `application.properties`

## Support

For support or questions, please create an issue in the repository.
