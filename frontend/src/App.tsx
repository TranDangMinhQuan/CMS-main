import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { AuthProvider } from './contexts/AuthContext';
import Header from './components/Header/Header';
import HomePage from './pages/HomePage/HomePage';
import LoginPage from './pages/LoginPage/LoginPage';
import RegisterPage from './pages/RegisterPage/RegisterPage';
import CourtsPage from './pages/CourtsPage/CourtsPage';
import CourtDetailPage from './pages/CourtDetailPage/CourtDetailPage';
import BookingPage from './pages/BookingPage/BookingPage';
import MyBookingsPage from './pages/MyBookingsPage/MyBookingsPage';
import AdminPage from './pages/AdminPage/AdminPage';
import './App.css';

function App() {
  return (
    <AuthProvider>
      <Router>
        <div className="App">
          <Header />
          <main className="main-content">
            <Routes>
              <Route path="/" element={<HomePage />} />
              <Route path="/login" element={<LoginPage />} />
              <Route path="/register" element={<RegisterPage />} />
              <Route path="/courts" element={<CourtsPage />} />
              <Route path="/courts/:id" element={<CourtDetailPage />} />
              <Route path="/booking/:courtId" element={<BookingPage />} />
              <Route path="/my-bookings" element={<MyBookingsPage />} />
              <Route path="/admin" element={<AdminPage />} />
            </Routes>
          </main>
        </div>
      </Router>
    </AuthProvider>
  );
}

export default App;
