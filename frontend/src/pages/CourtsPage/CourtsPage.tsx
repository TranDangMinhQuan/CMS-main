import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { Court, Review } from '../../types';
import { courtAPI, reviewAPI } from '../../services/api';
import { useAuth } from '../../context/AuthContext';

const CourtsPage: React.FC = () => {
  const [courts, setCourts] = useState<Court[]>([]);
  const [reviews, setReviews] = useState<{ [key: number]: Review[] }>({});
  const [ratings, setRatings] = useState<{ [key: number]: number }>({});
  const [loading, setLoading] = useState(true);
  const { isAuthenticated } = useAuth();

  useEffect(() => {
    fetchCourts();
  }, []);

  const fetchCourts = async () => {
    try {
      const courtsData = await courtAPI.getAllCourts();
      setCourts(courtsData);
      
      // Fetch reviews and ratings for each court
      for (const court of courtsData) {
        const courtReviews = await reviewAPI.getReviewsByCourt(court.id);
        const avgRating = await reviewAPI.getAverageRating(court.id);
        
        setReviews(prev => ({ ...prev, [court.id]: courtReviews }));
        setRatings(prev => ({ ...prev, [court.id]: avgRating }));
      }
    } catch (error) {
      console.error('Error fetching courts:', error);
    } finally {
      setLoading(false);
    }
  };

  const renderStars = (rating: number) => {
    const stars = [];
    for (let i = 1; i <= 5; i++) {
      stars.push(
        <span key={i} className={i <= rating ? 'star filled' : 'star'}>
          ⭐
        </span>
      );
    }
    return stars;
  };

  if (loading) {
    return (
      <div className="loading">
        <div className="spinner"></div>
      </div>
    );
  }

  return (
    <div className="courts-page">
      <div className="page-header">
        <h1>Available Courts</h1>
        <p>Choose from our premium badminton courts</p>
      </div>

      <div className="courts-grid grid grid-2">
        {courts.map((court) => (
          <div key={court.id} className="court-card card">
            <div className="court-header">
              <h3 className="court-name">{court.name}</h3>
              <div className="court-rating">
                {renderStars(Math.round(ratings[court.id] || 0))}
                <span className="rating-text">
                  ({ratings[court.id]?.toFixed(1) || '0.0'})
                </span>
              </div>
            </div>
            
            <div className="court-info">
              <p className="court-description">{court.description}</p>
              <div className="court-price">
                <strong>{court.hourlyRate.toLocaleString()} VND/hour</strong>
              </div>
              <div className="court-status">
                Status: <span className={`status ${court.status.toLowerCase()}`}>
                  {court.status}
                </span>
              </div>
            </div>

            <div className="court-reviews">
              <h4>Recent Reviews ({reviews[court.id]?.length || 0})</h4>
              {reviews[court.id]?.slice(0, 2).map((review) => (
                <div key={review.id} className="review-item">
                  <div className="review-header">
                    <strong>{review.user.fullName}</strong>
                    <div className="review-rating">
                      {renderStars(review.rating)}
                    </div>
                  </div>
                  <p className="review-comment">{review.comment}</p>
                </div>
              ))}
            </div>

            <div className="court-actions">
              {isAuthenticated && court.status === 'AVAILABLE' && (
                <Link to={`/booking?courtId=${court.id}`} className="btn btn-primary">
                  Book Now
                </Link>
              )}
              {!isAuthenticated && (
                <Link to="/login" className="btn btn-secondary">
                  Login to Book
                </Link>
              )}
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default CourtsPage;