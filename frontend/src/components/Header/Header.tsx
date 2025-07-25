import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../../contexts/AuthContext';
import './Header.module.css';

const Header: React.FC = () => {
  const { user, isAuthenticated, logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate('/');
  };

  return (
    <header className="header">
      <div className="header-container">
        <Link to="/" className="logo">
          🏸 BadmintonBooking
        </Link>
        
        <nav className="nav">
          <Link to="/" className="nav-link">Trang chủ</Link>
          <Link to="/courts" className="nav-link">Sân cầu lông</Link>
          
          {isAuthenticated ? (
            <>
              <Link to="/my-bookings" className="nav-link">Booking của tôi</Link>
              {(user?.role === 'STAFF' || user?.role === 'OWNER') && (
                <Link to="/admin" className="nav-link">Quản lý</Link>
              )}
              <div className="user-menu">
                <span className="user-info">Xin chào, {user?.fullName}</span>
                <button onClick={handleLogout} className="btn btn-secondary">
                  Đăng xuất
                </button>
              </div>
            </>
          ) : (
            <div className="auth-links">
              <Link to="/login" className="btn btn-primary">Đăng nhập</Link>
              <Link to="/register" className="btn btn-secondary">Đăng ký</Link>
            </div>
          )}
        </nav>
      </div>
    </header>
  );
};

export default Header;