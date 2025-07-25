import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { courtAPI } from '../services/api';
import { useAuth } from '../contexts/AuthContext';

const CourtCard = ({ id, name, image, price, location, rating, availableSlots, distance }) => {
  return (
    <div className="bg-white rounded-xl shadow-lg hover:shadow-xl transition-all duration-300 overflow-hidden group">
      <div className="relative overflow-hidden">
        <img 
          src={image} 
          alt={name}
          className="w-full h-48 object-cover group-hover:scale-105 transition-transform duration-300"
        />
        <div className="absolute top-3 right-3 bg-white/90 backdrop-blur-sm px-2 py-1 rounded-full flex items-center gap-1">
          <span className="text-yellow-500">⭐</span>
          <span className="text-sm font-medium">{rating}</span>
        </div>
        <div className="absolute top-3 left-3 bg-gradient-to-r from-blue-500 to-purple-600 text-white px-3 py-1 rounded-full text-sm font-medium">
          {distance}
        </div>
      </div>
      
      <div className="p-5">
        <h3 className="text-lg font-bold text-gray-800 mb-2">{name}</h3>
        
        <div className="flex items-center text-gray-600 mb-3">
          <span className="mr-1">📍</span>
          <span className="text-sm">{location}</span>
        </div>
        
        <div className="mb-4">
          <div className="flex items-center mb-2">
            <span className="text-green-500 mr-1">🕒</span>
            <span className="text-sm font-medium text-green-600">Khung giờ trống:</span>
          </div>
          <div className="flex flex-wrap gap-1">
            {availableSlots.map((slot, index) => (
              <span key={index} className="bg-green-50 text-green-700 px-2 py-1 rounded-md text-xs font-medium">
                {slot}
              </span>
            ))}
          </div>
        </div>
        
        <div className="flex items-center justify-between">
          <div className="text-right">
            <span className="text-2xl font-bold text-blue-600">
              {price.toLocaleString('vi-VN')}đ
            </span>
            <span className="text-gray-500 text-sm block">/90 phút</span>
          </div>
          <Link 
            to="/booking"
            className="bg-gradient-to-r from-blue-500 to-purple-600 hover:from-blue-600 hover:to-purple-700 text-white px-6 py-2 rounded-lg font-medium transition-all duration-200 transform hover:scale-105"
          >
            Đặt ngay
          </Link>
        </div>
      </div>
    </div>
  );
};

function HomePage() {
  const [searchTerm, setSearchTerm] = useState('');
  const [courts, setCourts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const { user, logout } = useAuth();

  useEffect(() => {
    fetchCourts();
  }, []);

  const fetchCourts = async () => {
    try {
      setLoading(true);
      const courtsData = await courtAPI.getAllCourts();
      setCourts(courtsData);
    } catch (err) {
      setError('Không thể tải dữ liệu sân. Vui lòng thử lại sau.');
      console.error('Error fetching courts:', err);
    } finally {
      setLoading(false);
    }
  };

  const filteredCourts = courts.filter(court =>
    court.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
    court.address.toLowerCase().includes(searchTerm.toLowerCase()) ||
    court.district.toLowerCase().includes(searchTerm.toLowerCase())
  );

  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 via-white to-purple-50">
      {/* Navigation */}
      <nav className="bg-white/80 backdrop-blur-md shadow-lg sticky top-0 z-50">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="flex justify-between items-center h-16">
            <Link to="/" className="flex items-center">
              <div className="bg-gradient-to-r from-blue-500 to-purple-600 text-white p-2 rounded-lg mr-3">
                🏸
              </div>
              <h1 className="text-xl font-bold bg-gradient-to-r from-blue-600 to-purple-600 bg-clip-text text-transparent">
                BadmintonBooking
              </h1>
            </Link>
            <div className="flex space-x-1">
              <Link to="/" className="px-4 py-2 rounded-lg font-medium text-gray-600 hover:text-blue-600 transition">🏠 Trang chủ</Link>
              <Link to="/booking" className="px-4 py-2 rounded-lg font-medium text-gray-600 hover:text-blue-600 transition">📅 Đặt sân</Link>
              <Link to="/history" className="px-4 py-2 rounded-lg font-medium text-gray-600 hover:text-blue-600 transition">📋 Lịch sử</Link>
              <Link to="/about" className="px-4 py-2 rounded-lg font-medium text-gray-600 hover:text-blue-600 transition">ℹ️ Về chúng tôi</Link>
              {user ? (
                <div className="flex items-center space-x-2">
                  <span className="px-4 py-2 text-gray-700">👋 Xin chào, {user.fullName || user.email}</span>
                  <button 
                    onClick={logout}
                    className="px-4 py-2 rounded-lg font-medium text-red-600 hover:text-red-800 transition"
                  >
                    🚪 Đăng xuất
                  </button>
                </div>
              ) : (
                <>
                  <Link to="/login" className="px-4 py-2 rounded-lg font-medium text-blue-600 hover:underline transition">🔐 Đăng nhập</Link>
                  <Link to="/signup" className="px-4 py-2 rounded-lg font-medium text-white bg-gradient-to-r from-green-500 to-green-600 hover:from-green-600 hover:to-green-700 transition">📝 Đăng ký</Link>
                </>
              )}
            </div>
          </div>
        </div>
      </nav>

      {/* Hero Section */}
      <div className="relative overflow-hidden">
        <div className="absolute inset-0 bg-gradient-to-r from-blue-600/20 to-purple-600/20"></div>
        <div className="relative max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-20">
          <div className="text-center">
            <h1 className="text-4xl md:text-6xl font-bold text-gray-800 mb-6">
              Đặt sân cầu lông
              <span className="block bg-gradient-to-r from-blue-600 to-purple-600 bg-clip-text text-transparent">
                Dễ dàng & Nhanh chóng
              </span>
            </h1>
            <p className="text-xl text-gray-600 mb-8 max-w-2xl mx-auto">
              Tìm và đặt sân cầu lông gần bạn với giá tốt nhất. Hệ thống đặt sân trực tuyến hiện đại và tiện lợi.
            </p>
            
            {/* Search Bar */}
            <div className="max-w-2xl mx-auto">
              <div className="relative">
                <span className="absolute left-4 top-1/2 transform -translate-y-1/2 text-gray-400 text-xl">🔍</span>
                <input
                  type="text"
                  placeholder="Tìm kiếm sân theo tên hoặc địa điểm..."
                  value={searchTerm}
                  onChange={(e) => setSearchTerm(e.target.value)}
                  className="w-full pl-12 pr-4 py-4 rounded-2xl border-2 border-gray-200 focus:border-blue-500 focus:outline-none text-lg shadow-lg"
                />
              </div>
            </div>
          </div>
        </div>
      </div>

      {/* Stats Section */}
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
        <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
          <div className="text-center p-6 bg-white rounded-2xl shadow-lg">
            <div className="bg-blue-100 w-16 h-16 rounded-full flex items-center justify-center mx-auto mb-4">
              <span className="text-2xl">🏸</span>
            </div>
            <h3 className="text-3xl font-bold text-gray-800 mb-2">50+</h3>
            <p className="text-gray-600">Sân cầu lông</p>
          </div>
          <div className="text-center p-6 bg-white rounded-2xl shadow-lg">
            <div className="bg-green-100 w-16 h-16 rounded-full flex items-center justify-center mx-auto mb-4">
              <span className="text-2xl">👥</span>
            </div>
            <h3 className="text-3xl font-bold text-gray-800 mb-2">1000+</h3>
            <p className="text-gray-600">Khách hàng hài lòng</p>
          </div>
          <div className="text-center p-6 bg-white rounded-2xl shadow-lg">
            <div className="bg-purple-100 w-16 h-16 rounded-full flex items-center justify-center mx-auto mb-4">
              <span className="text-2xl">⭐</span>
            </div>
            <h3 className="text-3xl font-bold text-gray-800 mb-2">4.8</h3>
            <p className="text-gray-600">Đánh giá trung bình</p>
          </div>
        </div>
      </div>

      {/* Courts Section */}
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
        <div className="text-center mb-12">
          <h2 className="text-3xl md:text-4xl font-bold text-gray-800 mb-4">
            Sân đang hoạt động
          </h2>
          <p className="text-xl text-gray-600">
            Những sân cầu lông chất lượng cao với giá cả hợp lý
          </p>
        </div>

        {loading ? (
          <div className="text-center py-12">
            <div className="text-6xl mb-4">⏳</div>
            <h3 className="text-xl font-semibold text-gray-600 mb-2">Đang tải dữ liệu...</h3>
            <p className="text-gray-500">Vui lòng chờ trong giây lát</p>
          </div>
        ) : error ? (
          <div className="text-center py-12">
            <div className="text-6xl mb-4">❌</div>
            <h3 className="text-xl font-semibold text-red-600 mb-2">Có lỗi xảy ra</h3>
            <p className="text-gray-500 mb-4">{error}</p>
            <button 
              onClick={fetchCourts}
              className="px-6 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition"
            >
              Thử lại
            </button>
          </div>
        ) : (
          <>
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
              {filteredCourts.map((court) => (
                <CourtCard 
                  key={court.id} 
                  id={court.id}
                  name={court.name}
                  image={court.imageUrl}
                  price={court.pricePerHour}
                  location={`${court.district}, ${court.city}`}
                  rating={court.rating}
                  availableSlots={court.availableSlots || ["Liên hệ để biết lịch trống"]}
                  distance="Đang tính..."
                />
              ))}
            </div>

            {filteredCourts.length === 0 && !loading && (
              <div className="text-center py-12">
                <div className="text-6xl mb-4">🔍</div>
                <h3 className="text-xl font-semibold text-gray-600 mb-2">Không tìm thấy sân nào</h3>
                <p className="text-gray-500">Vui lòng thử lại với từ khóa khác</p>
              </div>
            )}
          </>
        )}
      </div>

      {/* Features Section */}
      <div className="bg-gradient-to-r from-blue-600 to-purple-600 py-16">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="text-center mb-12">
            <h2 className="text-3xl md:text-4xl font-bold text-white mb-4">
              Tại sao chọn chúng tôi?
            </h2>
          </div>
          
          <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
            <div className="text-center text-white">
              <div className="bg-white/20 w-16 h-16 rounded-full flex items-center justify-center mx-auto mb-4">
                <span className="text-3xl">🕒</span>
              </div>
              <h3 className="text-xl font-bold mb-2">Đặt sân 24/7</h3>
              <p className="text-blue-100">Hệ thống hoạt động liên tục, đặt sân bất cứ lúc nào</p>
            </div>
            <div className="text-center text-white">
              <div className="bg-white/20 w-16 h-16 rounded-full flex items-center justify-center mx-auto mb-4">
                <span className="text-3xl">📍</span>
              </div>
              <h3 className="text-xl font-bold mb-2">Vị trí thuận lợi</h3>
              <p className="text-blue-100">Sân gần bạn nhất với đầy đủ tiện ích</p>
            </div>
            <div className="text-center text-white">
              <div className="bg-white/20 w-16 h-16 rounded-full flex items-center justify-center mx-auto mb-4">
                <span className="text-3xl">⭐</span>
              </div>
              <h3 className="text-xl font-bold mb-2">Chất lượng cao</h3>
              <p className="text-blue-100">Sân chuẩn thi đấu với dịch vụ chuyên nghiệp</p>
            </div>
          </div>
        </div>
      </div>

      {/* Footer */}
      <footer className="bg-gray-900 text-white py-12">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
            <div>
              <div className="flex items-center mb-4">
                <div className="bg-gradient-to-r from-blue-500 to-purple-600 text-white p-2 rounded-lg mr-3">
                  🏸
                </div>
                <h3 className="text-xl font-bold">BadmintonBooking</h3>
              </div>
              <p className="text-gray-400">
                Nền tảng đặt sân cầu lông hàng đầu tại Việt Nam
              </p>
            </div>
            
            <div>
              <h4 className="text-lg font-semibold mb-4">Liên hệ</h4>
              <div className="space-y-2">
                <div className="flex items-center">
                  <span className="mr-2">📞</span>
                  <span className="text-gray-400">0123 456 789</span>
                </div>
                <div className="flex items-center">
                  <span className="mr-2">📧</span>
                  <span className="text-gray-400">support@badmintonbooking.vn</span>
                </div>
              </div>
            </div>
            
            <div>
              <h4 className="text-lg font-semibold mb-4">Giờ hoạt động</h4>
              <div className="text-gray-400">
                <p>Thứ 2 - Chủ nhật</p>
                <p>6:00 - 22:00</p>
              </div>
            </div>
          </div>
          
          <div className="border-t border-gray-800 mt-8 pt-8 text-center text-gray-400">
            <p>&copy; 2025 BadmintonBooking. All rights reserved.</p>
          </div>
        </div>
      </footer>
    </div>
  );
}

export default HomePage;