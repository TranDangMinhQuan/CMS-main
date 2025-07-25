import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../../context/AuthContext';
import './Navbar.module.css';

const Navbar: React.FC = () => {
  const { isAuthenticated, user, logout, isStaff, isOwner } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate('/');
  };

  return (
    <nav className="navbar">
      <div className="navbar-container">
        <Link to="/" className="navbar-brand">
          🏸 Badminton Booking
        </Link>
        
        <div className="navbar-menu">
          <Link to="/courts" className="navbar-link">
            Courts
          </Link>
          
          {isAuthenticated ? (
            <>
              <Link to="/booking" className="navbar-link">
                Book Court
              </Link>
              <Link to="/my-bookings" className="navbar-link">
                My Bookings
              </Link>
              
              {(isStaff || isOwner) && (
                <Link to="/dashboard" className="navbar-link">
                  Dashboard
                </Link>
              )}
              
              {isOwner && (
                <Link to="/admin" className="navbar-link">
                  Admin
                </Link>
              )}
              
              <div className="navbar-user">
                <span className="navbar-welcome">
                  Welcome, {user?.fullName}
                </span>
                <button onClick={handleLogout} className="btn btn-secondary">
                  Logout
                </button>
              </div>
            </>
          ) : (
            <div className="navbar-auth">
              <Link to="/login" className="btn btn-primary">
                Login
              </Link>
              <Link to="/register" className="btn btn-secondary">
                Register
              </Link>
            </div>
          )}
        </div>
      </div>
    </nav>
  );
};

export default Navbar;