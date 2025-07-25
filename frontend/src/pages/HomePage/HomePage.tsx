import React from 'react';
import { Link } from 'react-router-dom';
import { useAuth } from '../../context/AuthContext';
import './HomePage.module.css';

const HomePage: React.FC = () => {
  const { isAuthenticated } = useAuth();

  return (
    <div className="homepage">
      <div className="hero-section">
        <div className="hero-content">
          <h1 className="hero-title">🏸 Badminton Court Booking System</h1>
          <p className="hero-subtitle">
            Book your favorite badminton court online and enjoy playing with friends!
          </p>
          <div className="hero-actions">
            <Link to="/courts" className="btn btn-primary btn-large">
              View Courts
            </Link>
            {!isAuthenticated && (
              <Link to="/register" className="btn btn-secondary btn-large">
                Join Now
              </Link>
            )}
          </div>
        </div>
      </div>

      <div className="features-section">
        <div className="container">
          <h2 className="section-title">Why Choose Our Courts?</h2>
          <div className="features-grid">
            <div className="feature-card">
              <div className="feature-icon">🏆</div>
              <h3>Premium Quality Courts</h3>
              <p>Professional-grade badminton courts with proper lighting and ventilation</p>
            </div>
            <div className="feature-card">
              <div className="feature-icon">💰</div>
              <h3>Affordable Pricing</h3>
              <p>Competitive hourly rates with flexible booking options</p>
            </div>
            <div className="feature-card">
              <div className="feature-icon">📱</div>
              <h3>Easy Online Booking</h3>
              <p>Book courts instantly through our user-friendly platform</p>
            </div>
            <div className="feature-card">
              <div className="feature-icon">🎯</div>
              <h3>Equipment Rental</h3>
              <p>Rent high-quality rackets for only 30,000 VND per racket</p>
            </div>
          </div>
        </div>
      </div>

      <div className="cta-section">
        <div className="container">
          <h2>Ready to Play?</h2>
          <p>Join thousands of satisfied customers who trust our badminton courts</p>
          {isAuthenticated ? (
            <Link to="/booking" className="btn btn-success btn-large">
              Book a Court Now
            </Link>
          ) : (
            <div className="cta-actions">
              <Link to="/login" className="btn btn-primary btn-large">
                Login
              </Link>
              <Link to="/register" className="btn btn-success btn-large">
                Register
              </Link>
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default HomePage;