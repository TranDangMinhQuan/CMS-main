import React, { useState } from 'react';
import { Link } from 'react-router-dom';

// Sample booking history data
const bookingHistory = [
  {
    id: "BK001",
    date: "2025-07-20",
    bookingDate: "2025-07-18",
    court: {
      name: "Sân A - Quận 1",
      location: "Quận 1, TP.HCM",
      image: "https://cdn.tuoitre.vn/thumb_w/730/471584752817336320/2023/7/6/san-cau-long-16886087164821647664781-1688608954969820211575.jpg"
    },
    timeSlots: ["08:00-09:30", "14:00-15:30"],
    totalAmount: 240000,
    status: "completed",
    paymentMethod: "Chuyển khoản",
    playerCount: 4
  },
  {
    id: "BK002",
    date: "2025-07-22",
    bookingDate: "2025-07-21",
    court: {
      name: "Sân C - Bình Thạnh",
      location: "Bình Thạnh, TP.HCM",
      image: "https://top10tphcm.com/wp-content/uploads/2020/09/San-cau-long-Binh-Quoi.jpg"
    },
    timeSlots: ["19:00-20:30"],
    totalAmount: 150000,
    status: "completed",
    paymentMethod: "Tiền mặt",
    playerCount: 2
  },
  {
    id: "BK003",
    date: "2025-07-25",
    bookingDate: "2025-07-24",
    court: {
      name: "Sân B - Quận 5",
      location: "Quận 5, TP.HCM",
      image: "https://statics.vinpearl.com/san-cau-long-ha-noi-1_1682561731.jpg"
    },
    timeSlots: ["20:00-21:30"],
    totalAmount: 100000,
    status: "upcoming",
    paymentMethod: "Ví điện tử",
    playerCount: 2
  },
  {
    id: "BK004",
    date: "2025-07-15",
    bookingDate: "2025-07-14",
    court: {
      name: "Sân A - Quận 1",
      location: "Quận 1, TP.HCM",
      image: "https://cdn.tuoitre.vn/thumb_w/730/471584752817336320/2023/7/6/san-cau-long-16886087164821647664781-1688608954969820211575.jpg"
    },
    timeSlots: ["16:00-17:30"],
    totalAmount: 120000,
    status: "cancelled",
    paymentMethod: "Chuyển khoản",
    playerCount: 4,
    refundAmount: 96000
  }
];

const Navigation = () => (
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
          <Link to="/" className="px-4 py-2 rounded-lg font-medium text-gray-600 hover:text-blue-600 transition-all duration-200">
            🏠 Trang chủ
          </Link>
          <Link to="/booking" className="px-4 py-2 rounded-lg font-medium text-gray-600 hover:text-blue-600 transition-all duration-200">
            📅 Đặt sân
          </Link>
          <Link to="/history" className="px-4 py-2 rounded-lg font-medium bg-gradient-to-r from-blue-500 to-purple-600 text-white">
            📋 Lịch sử
          </Link>
          <Link to="/about" className="px-4 py-2 rounded-lg font-medium text-gray-600 hover:text-blue-600 transition-all duration-200">
            ℹ️ Về chúng tôi
          </Link>
        </div>
      </div>
    </div>
  </nav>
);

const StatusBadge = ({ status }) => {
  const statusConfig = {
    completed: { bg: "bg-green-100", text: "text-green-800", label: "Hoàn thành", icon: "✅" },
    upcoming: { bg: "bg-blue-100", text: "text-blue-800", label: "Sắp tới", icon: "⏰" },
    cancelled: { bg: "bg-red-100", text: "text-red-800", label: "Đã hủy", icon: "❌" }
  };

  const config = statusConfig[status] || statusConfig.completed;

  return (
    <span className={`${config.bg} ${config.text} px-3 py-1 rounded-full text-sm font-medium flex items-center gap-1 w-fit`}>
      <span>{config.icon}</span>
      {config.label}
    </span>
  );
};

const BookingCard = ({ booking, onViewDetails }) => {
  return (
    <div className="bg-white rounded-xl shadow-lg hover:shadow-xl transition-all duration-300 overflow-hidden">
      <div className="flex flex-col md:flex-row">
        <div className="md:w-1/3">
          <img 
            src={booking.court.image} 
            alt={booking.court.name}
            className="w-full h-48 md:h-full object-cover"
          />
        </div>
        
        <div className="flex-1 p-6">
          <div className="flex flex-col sm:flex-row sm:justify-between sm:items-start mb-4">
            <div className="mb-4 sm:mb-0">
              <h3 className="text-xl font-bold text-gray-800 mb-1">{booking.court.name}</h3>
              <div className="flex items-center text-gray-600 mb-2">
                <span className="mr-1">📍</span>
                <span className="text-sm">{booking.court.location}</span>
              </div>
              <div className="text-sm text-gray-500">
                Mã đặt sân: <span className="font-mono font-medium">{booking.id}</span>
              </div>
            </div>
            <StatusBadge status={booking.status} />
          </div>

          <div className="grid grid-cols-1 sm:grid-cols-2 gap-4 mb-4">
            <div>
              <div className="text-sm text-gray-600 mb-1">📅 Ngày chơi:</div>
              <div className="font-semibold">{booking.date}</div>
            </div>
            <div>
              <div className="text-sm text-gray-600 mb-1">🕒 Khung giờ:</div>
              <div className="font-semibold">{booking.timeSlots.join(", ")}</div>
            </div>
            <div>
              <div className="text-sm text-gray-600 mb-1">👥 Số người chơi:</div>
              <div className="font-semibold">{booking.playerCount} người</div>
            </div>
            <div>
              <div className="text-sm text-gray-600 mb-1">💳 Thanh toán:</div>
              <div className="font-semibold">{booking.paymentMethod}</div>
            </div>
          </div>

          <div className="flex flex-col sm:flex-row sm:justify-between sm:items-center">
            <div className="mb-4 sm:mb-0">
              <div className="text-sm text-gray-600">Tổng tiền:</div>
              <div className="text-2xl font-bold text-blue-600">
                {booking.totalAmount.toLocaleString('vi-VN')}đ
              </div>
              {booking.status === 'cancelled' && booking.refundAmount && (
                <div className="text-sm text-green-600">
                  Hoàn tiền: {booking.refundAmount.toLocaleString('vi-VN')}đ
                </div>
              )}
            </div>
            <button
              onClick={() => onViewDetails(booking)}
              className="bg-gradient-to-r from-blue-500 to-purple-600 hover:from-blue-600 hover:to-purple-700 text-white px-6 py-2 rounded-lg font-medium transition-all duration-200 w-full sm:w-auto"
            >
              Xem chi tiết
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

function HistoryPage() {
  const [filterStatus, setFilterStatus] = useState('all');
  const [selectedBooking, setSelectedBooking] = useState(null);
  const [showDetails, setShowDetails] = useState(false);

  const filteredBookings = bookingHistory.filter(booking => 
    filterStatus === 'all' || booking.status === filterStatus
  );

  const totalSpent = bookingHistory
    .filter(b => b.status === 'completed')
    .reduce((sum, booking) => sum + booking.totalAmount, 0);

  const totalBookings = bookingHistory.length;
  const completedBookings = bookingHistory.filter(b => b.status === 'completed').length;

  const handleViewDetails = (booking) => {
    setSelectedBooking(booking);
    setShowDetails(true);
  };

  const handleCancelBooking = () => {
    if (window.confirm('Bạn có chắc chắn muốn hủy đặt sân này?')) {
      alert('Đặt sân đã được hủy thành công!');
      setShowDetails(false);
    }
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 via-white to-purple-50">
      <Navigation />
      
      {/* Header */}
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        <div className="text-center mb-8">
          <h1 className="text-3xl md:text-4xl font-bold text-gray-800 mb-4">
            Lịch sử đặt sân
          </h1>
          <p className="text-lg text-gray-600">
            Quản lý và theo dõi các lần đặt sân của bạn
          </p>
        </div>

        {/* Stats Cards */}
        <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mb-8">
          <div className="bg-white rounded-xl shadow-lg p-6 text-center">
            <div className="bg-blue-100 w-16 h-16 rounded-full flex items-center justify-center mx-auto mb-4">
              <span className="text-2xl">📊</span>
            </div>
            <h3 className="text-2xl font-bold text-gray-800 mb-2">{totalBookings}</h3>
            <p className="text-gray-600">Tổng số lần đặt</p>
          </div>
          <div className="bg-white rounded-xl shadow-lg p-6 text-center">
            <div className="bg-green-100 w-16 h-16 rounded-full flex items-center justify-center mx-auto mb-4">
              <span className="text-2xl">✅</span>
            </div>
            <h3 className="text-2xl font-bold text-gray-800 mb-2">{completedBookings}</h3>
            <p className="text-gray-600">Đã hoàn thành</p>
          </div>
          <div className="bg-white rounded-xl shadow-lg p-6 text-center">
            <div className="bg-purple-100 w-16 h-16 rounded-full flex items-center justify-center mx-auto mb-4">
              <span className="text-2xl">💰</span>
            </div>
            <h3 className="text-2xl font-bold text-gray-800 mb-2">{totalSpent.toLocaleString('vi-VN')}đ</h3>
            <p className="text-gray-600">Tổng chi tiêu</p>
          </div>
        </div>

        {/* Filter Tabs */}
        <div className="bg-white rounded-lg shadow-md p-6 mb-8">
          <div className="flex flex-wrap gap-2">
            <button
              onClick={() => setFilterStatus('all')}
              className={`px-4 py-2 rounded-lg font-medium transition-all duration-200 ${
                filterStatus === 'all'
                  ? 'bg-gradient-to-r from-blue-500 to-purple-600 text-white'
                  : 'bg-gray-100 text-gray-700 hover:bg-gray-200'
              }`}
            >
              📋 Tất cả ({bookingHistory.length})
            </button>
            <button
              onClick={() => setFilterStatus('completed')}
              className={`px-4 py-2 rounded-lg font-medium transition-all duration-200 ${
                filterStatus === 'completed'
                  ? 'bg-gradient-to-r from-green-500 to-green-600 text-white'
                  : 'bg-gray-100 text-gray-700 hover:bg-gray-200'
              }`}
            >
              ✅ Hoàn thành ({bookingHistory.filter(b => b.status === 'completed').length})
            </button>
            <button
              onClick={() => setFilterStatus('upcoming')}
              className={`px-4 py-2 rounded-lg font-medium transition-all duration-200 ${
                filterStatus === 'upcoming'
                  ? 'bg-gradient-to-r from-blue-500 to-blue-600 text-white'
                  : 'bg-gray-100 text-gray-700 hover:bg-gray-200'
              }`}
            >
              ⏰ Sắp tới ({bookingHistory.filter(b => b.status === 'upcoming').length})
            </button>
            <button
              onClick={() => setFilterStatus('cancelled')}
              className={`px-4 py-2 rounded-lg font-medium transition-all duration-200 ${
                filterStatus === 'cancelled'
                  ? 'bg-gradient-to-r from-red-500 to-red-600 text-white'
                  : 'bg-gray-100 text-gray-700 hover:bg-gray-200'
              }`}
            >
              ❌ Đã hủy ({bookingHistory.filter(b => b.status === 'cancelled').length})
            </button>
          </div>
        </div>

        {/* Booking History List */}
        <div className="space-y-6">
          {filteredBookings.length > 0 ? (
            filteredBookings.map((booking) => (
              <BookingCard
                key={booking.id}
                booking={booking}
                onViewDetails={handleViewDetails}
              />
            ))
          ) : (
            <div className="text-center py-12">
              <div className="text-6xl mb-4">📋</div>
              <h3 className="text-xl font-semibold text-gray-600 mb-2">Chưa có lịch sử đặt sân</h3>
              <p className="text-gray-500 mb-6">Hãy đặt sân đầu tiên của bạn!</p>
              <Link
                to="/booking"
                className="inline-block bg-gradient-to-r from-blue-500 to-purple-600 hover:from-blue-600 hover:to-purple-700 text-white px-6 py-3 rounded-lg font-medium transition-all duration-200"
              >
                Đặt sân ngay
              </Link>
            </div>
          )}
        </div>
      </div>

      {/* Booking Details Modal */}
      {showDetails && selectedBooking && (
        <div className="fixed inset-0 bg-black/50 flex items-center justify-center z-50 p-4">
          <div className="bg-white rounded-xl shadow-2xl max-w-2xl w-full max-h-[80vh] overflow-y-auto">
            <div className="p-6">
              <div className="flex items-center justify-between mb-6">
                <h2 className="text-2xl font-bold text-gray-800">Chi tiết đặt sân</h2>
                <button
                  onClick={() => setShowDetails(false)}
                  className="text-gray-500 hover:text-gray-700 text-2xl"
                >
                  ✕
                </button>
              </div>

              <div className="space-y-6">
                {/* Court Info */}
                <div className="flex gap-4">
                  <img 
                    src={selectedBooking.court.image} 
                    alt={selectedBooking.court.name}
                    className="w-24 h-24 object-cover rounded-lg"
                  />
                  <div className="flex-1">
                    <h3 className="text-lg font-bold text-gray-800">{selectedBooking.court.name}</h3>
                    <p className="text-gray-600 flex items-center">
                      <span className="mr-1">📍</span>
                      {selectedBooking.court.location}
                    </p>
                    <div className="mt-2">
                      <StatusBadge status={selectedBooking.status} />
                    </div>
                  </div>
                </div>

                {/* Booking Details */}
                <div className="bg-gray-50 rounded-lg p-4">
                  <h4 className="font-semibold text-gray-800 mb-3">Thông tin đặt sân</h4>
                  <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
                    <div>
                      <span className="text-sm text-gray-600">Mã đặt sân:</span>
                      <p className="font-mono font-medium">{selectedBooking.id}</p>
                    </div>
                    <div>
                      <span className="text-sm text-gray-600">Ngày đặt:</span>
                      <p className="font-medium">{selectedBooking.bookingDate}</p>
                    </div>
                    <div>
                      <span className="text-sm text-gray-600">Ngày chơi:</span>
                      <p className="font-medium">{selectedBooking.date}</p>
                    </div>
                    <div>
                      <span className="text-sm text-gray-600">Số người chơi:</span>
                      <p className="font-medium">{selectedBooking.playerCount} người</p>
                    </div>
                  </div>
                </div>

                {/* Time Slots */}
                <div>
                  <h4 className="font-semibold text-gray-800 mb-3">Khung giờ đã đặt</h4>
                  <div className="flex flex-wrap gap-2">
                    {selectedBooking.timeSlots.map((slot, index) => (
                      <span key={index} className="bg-blue-100 text-blue-800 px-3 py-1 rounded-md font-medium">
                        🕒 {slot}
                      </span>
                    ))}
                  </div>
                </div>

                {/* Payment Info */}
                <div className="bg-gray-50 rounded-lg p-4">
                  <h4 className="font-semibold text-gray-800 mb-3">Thông tin thanh toán</h4>
                  <div className="space-y-2">
                    <div className="flex justify-between">
                      <span className="text-gray-600">Phương thức:</span>
                      <span className="font-medium">{selectedBooking.paymentMethod}</span>
                    </div>
                    <div className="flex justify-between">
                      <span className="text-gray-600">Tổng tiền:</span>
                      <span className="font-bold text-blue-600 text-lg">
                        {selectedBooking.totalAmount.toLocaleString('vi-VN')}đ
                      </span>
                    </div>
                    {selectedBooking.status === 'cancelled' && selectedBooking.refundAmount && (
                      <div className="flex justify-between border-t pt-2">
                        <span className="text-gray-600">Số tiền hoàn:</span>
                        <span className="font-bold text-green-600">
                          {selectedBooking.refundAmount.toLocaleString('vi-VN')}đ
                        </span>
                      </div>
                    )}
                  </div>
                </div>

                {/* Action Buttons */}
                <div className="flex flex-col sm:flex-row gap-4">
                  <button
                    onClick={() => setShowDetails(false)}
                    className="flex-1 bg-gray-500 hover:bg-gray-600 text-white py-3 rounded-lg font-medium transition-all duration-200"
                  >
                    Đóng
                  </button>
                  {selectedBooking.status === 'upcoming' && (
                    <button
                      className="flex-1 bg-red-500 hover:bg-red-600 text-white py-3 rounded-lg font-medium transition-all duration-200"
                      onClick={handleCancelBooking}
                    >
                      Hủy đặt sân
                    </button>
                  )}
                </div>
              </div>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}

export default HistoryPage;