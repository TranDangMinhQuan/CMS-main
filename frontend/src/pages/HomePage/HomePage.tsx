import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { Court } from '../../types';
import { courtsAPI } from '../../services/api';
import './HomePage.module.css';

const HomePage: React.FC = () => {
  const [featuredCourts, setFeaturedCourts] = useState<Court[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchFeaturedCourts = async () => {
      try {
        const courts = await courtsAPI.getAllCourtsPublic();
        setFeaturedCourts(courts.slice(0, 3)); // Show first 3 courts
      } catch (error) {
        console.error('Error fetching courts:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchFeaturedCourts();
  }, []);

  return (
    <div className="home-page">
      {/* Hero Section */}
      <section className="hero">
        <div className="hero-content">
          <h1 className="hero-title">Đặt Sân Cầu Lông Dễ Dàng</h1>
          <p className="hero-description">
            Hệ thống đặt sân cầu lông hiện đại, tiện lợi và nhanh chóng. 
            Tìm kiếm sân phù hợp, xem đánh giá từ người dùng và đặt sân ngay hôm nay!
          </p>
          <div className="hero-actions">
            <Link to="/courts" className="btn btn-primary btn-large">
              Xem tất cả sân
            </Link>
            <Link to="/register" className="btn btn-secondary btn-large">
              Đăng ký ngay
            </Link>
          </div>
        </div>
      </section>

      {/* Features Section */}
      <section className="features">
        <div className="container">
          <h2 className="section-title">Tại sao chọn chúng tôi?</h2>
          <div className="features-grid">
            <div className="feature-card">
              <div className="feature-icon">🏸</div>
              <h3>Sân chất lượng cao</h3>
              <p>Các sân cầu lông được trang bị hiện đại, sạch sẽ và an toàn</p>
            </div>
            <div className="feature-card">
              <div className="feature-icon">⚡</div>
              <h3>Đặt sân nhanh chóng</h3>
              <p>Hệ thống đặt sân trực tuyến tiện lợi, chỉ với vài cú click</p>
            </div>
            <div className="feature-card">
              <div className="feature-icon">💳</div>
              <h3>Thanh toán linh hoạt</h3>
              <p>Hỗ trợ thanh toán online và offline, áp dụng coupon giảm giá</p>
            </div>
            <div className="feature-card">
              <div className="feature-icon">⭐</div>
              <h3>Đánh giá minh bạch</h3>
              <p>Xem đánh giá từ người dùng thực để chọn sân phù hợp nhất</p>
            </div>
          </div>
        </div>
      </section>

      {/* Featured Courts Section */}
      <section className="featured-courts">
        <div className="container">
          <h2 className="section-title">Sân nổi bật</h2>
          {loading ? (
            <div className="loading">
              <div className="spinner"></div>
            </div>
          ) : (
            <div className="courts-grid">
              {featuredCourts.map((court) => (
                <div key={court.id} className="court-card">
                  <div className="court-image">
                    {court.imageUrl ? (
                      <img src={court.imageUrl} alt={court.courtName} />
                    ) : (
                      <div className="court-placeholder">🏸</div>
                    )}
                  </div>
                  <div className="court-info">
                    <h3 className="court-name">{court.courtName}</h3>
                    <p className="court-description">{court.description}</p>
                    <div className="court-price">
                      {court.pricePerHour.toLocaleString('vi-VN')} VNĐ/giờ
                    </div>
                    <Link 
                      to={`/courts/${court.id}`} 
                      className="btn btn-primary court-btn"
                    >
                      Xem chi tiết
                    </Link>
                  </div>
                </div>
              ))}
            </div>
          )}
          <div className="section-footer">
            <Link to="/courts" className="btn btn-secondary">
              Xem tất cả sân
            </Link>
          </div>
        </div>
      </section>

      {/* CTA Section */}
      <section className="cta">
        <div className="container">
          <h2>Bắt đầu đặt sân ngay hôm nay!</h2>
          <p>Tham gia cộng đồng người chơi cầu lông và trải nghiệm dịch vụ tốt nhất</p>
          <Link to="/register" className="btn btn-primary btn-large">
            Đăng ký miễn phí
          </Link>
        </div>
      </section>
    </div>
  );
};

export default HomePage;