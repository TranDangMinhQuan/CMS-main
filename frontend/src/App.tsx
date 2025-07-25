import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { AuthProvider } from './context/AuthContext';
import Navbar from './components/Navbar/Navbar';
import HomePage from './pages/HomePage/HomePage';
import LoginPage from './pages/LoginPage/LoginPage';
import RegisterPage from './pages/RegisterPage/RegisterPage';
import CourtsPage from './pages/CourtsPage/CourtsPage';
import BookingPage from './pages/BookingPage/BookingPage';
import MyBookingsPage from './pages/MyBookingsPage/MyBookingsPage';
import DashboardPage from './pages/DashboardPage/DashboardPage';
import AdminPage from './pages/AdminPage/AdminPage';
import ProtectedRoute from './components/ProtectedRoute/ProtectedRoute';
import './App.css';

function App() {
  return (
    <AuthProvider>
      <Router>
        <div className="App">
          <Navbar />
          <main className="main-content">
            <Routes>
              <Route path="/" element={<HomePage />} />
              <Route path="/login" element={<LoginPage />} />
              <Route path="/register" element={<RegisterPage />} />
              <Route path="/courts" element={<CourtsPage />} />
              
              {/* Protected Routes for Members */}
              <Route path="/booking" element={
                <ProtectedRoute requiredRoles={['MEMBER', 'STAFF', 'OWNER']}>
                  <BookingPage />
                </ProtectedRoute>
              } />
              <Route path="/my-bookings" element={
                <ProtectedRoute requiredRoles={['MEMBER', 'STAFF', 'OWNER']}>
                  <MyBookingsPage />
                </ProtectedRoute>
              } />
              
              {/* Protected Routes for Staff */}
              <Route path="/dashboard" element={
                <ProtectedRoute requiredRoles={['STAFF', 'OWNER']}>
                  <DashboardPage />
                </ProtectedRoute>
              } />
              
              {/* Protected Routes for Owner */}
              <Route path="/admin" element={
                <ProtectedRoute requiredRoles={['OWNER']}>
                  <AdminPage />
                </ProtectedRoute>
              } />
              
              <Route path="*" element={<Navigate to="/" replace />} />
            </Routes>
          </main>
        </div>
      </Router>
    </AuthProvider>
  );
}

export default App;
