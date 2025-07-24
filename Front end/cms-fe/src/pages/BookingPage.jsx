import React, { useState } from 'react';
import { Link } from 'react-router-dom';

const courts = [
  {
    id: 1,
    name: "Sân A - Quận 1",
    image: "https://cdn.tuoitre.vn/thumb_w/730/471584752817336320/2023/7/6/san-cau-long-16886087164821647664781-1688608954969820211575.jpg",
    price: 120000,
    location: "Quận 1, TP.HCM",
    rating: 4.8,
    availableSlots: [
      { time: "06:00-07:30", status: "available", date: "2025-07-24" },
      { time: "08:00-09:30", status: "available", date: "2025-07-24" },
      { time: "10:00-11:30", status: "booked", date: "2025-07-24" },
      { time: "14:00-15:30", status: "available", date: "2025-07-24" },
      { time: "16:00-17:30", status: "booked", date: "2025-07-24" },
      { time: "19:00-20:30", status: "available", date: "2025-07-24" },
      { time: "21:00-22:30", status: "available", date: "2025-07-24" }
    ],
    facilities: ["Điều hòa", "Đèn LED", "Nước uống miễn phí", "Thay đồ"]
  },
  {
    id: 2,
    name: "Sân B - Quận 5",
    image: "https://statics.vinpearl.com/san-cau-long-ha-noi-1_1682561731.jpg",
    price: 100000,
    location: "Quận 5, TP.HCM",
    rating: 4.6,
    availableSlots: [
      { time: "07:00-08:30", status: "available", date: "2025-07-24" },
      { time: "09:00-10:30", status: "available", date: "2025-07-24" },
      { time: "11:00-12:30", status: "booked", date: "2025-07-24" },
      { time: "15:00-16:30", status: "available", date: "2025-07-24" },
      { time: "17:00-18:30", status: "available", date: "2025-07-24" },
      { time: "20:00-21:30", status: "booked", date: "2025-07-24" }
    ],
    facilities: ["Quạt trần", "Đèn halogen", "Nước uống", "Giữ xe miễn phí"]
  },
  {
    id: 3,
    name: "Sân C - Bình Thạnh",
    image: "https://top10tphcm.com/wp-content/uploads/2020/09/San-cau-long-Binh-Quoi.jpg",
    price: 150000,
    location: "Bình Thạnh, TP.HCM",
    rating: 4.9,
    availableSlots: [
      { time: "06:30-08:00", status: "available", date: "2025-07-24" },
      { time: "08:30-10:00", status: "booked", date: "2025-07-24" },
      { time: "10:30-12:00", status: "available", date: "2025-07-24" },
      { time: "13:00-14:30", status: "available", date: "2025-07-24" },
      { time: "15:00-16:30", status: "available", date: "2025-07-24" },
      { time: "18:00-19:30", status: "booked", date: "2025-07-24" },
      { time: "20:00-21:30", status: "available", date: "2025-07-24" }
    ],
    facilities: ["Điều hòa VIP", "Đèn LED cao cấp", "Nước lọc miễn phí", "Phòng thay đồ VIP", "Wifi miễn phí"]
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
          <Link to="/booking" className="px-4 py-2 rounded-lg font-medium bg-gradient-to-r from-blue-500 to-purple-600 text-white">
            📅 Đặt sân
          </Link>
          <Link to="/history" className="px-4 py-2 rounded-lg font-medium text-gray-600 hover:text-blue-600 transition-all duration-200">
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

const CourtBookingCard = ({ court, onBookSlot, selectedSlots }) => {
  return (
    <div className="bg-white rounded-xl shadow-lg overflow-hidden">
      <div className="relative">
        <img 
          src={court.image} 
          alt={court.name}
          className="w-full h-48 object-cover"
        />
        <div className="absolute top-3 right-3 bg-white/90 backdrop-blur-sm px-2 py-1 rounded-full flex items-center gap-1">
          <span className="text-yellow-500">⭐</span>
          <span className="text-sm font-medium">{court.rating}</span>
        </div>
      </div>
      
      <div className="p-6">
        <h3 className="text-xl font-bold text-gray-800 mb-2">{court.name}</h3>
        
        <div className="flex items-center text-gray-600 mb-4">
          <span className="mr-1">📍</span>
          <span className="text-sm">{court.location}</span>
        </div>

        <div className="mb-4">
          <h4 className="font-semibold text-gray-800 mb-2">Tiện ích:</h4>
          <div className="flex flex-wrap gap-2">
            {court.facilities.map((facility, index) => (
              <span key={index} className="bg-blue-50 text-blue-700 px-2 py-1 rounded-md text-xs">
                {facility}
              </span>
            ))}
          </div>
        </div>

        <div className="mb-4">
          <div className="flex items-center justify-between mb-3">
            <h4 className="font-semibold text-gray-800">Lịch trình hôm nay:</h4>
            <span className="text-lg font-bold text-blue-600">
              {court.price.toLocaleString('vi-VN')}đ/90 phút
            </span>
          </div>
          
          <div className="grid grid-cols-2 gap-2">
            {court.availableSlots.map((slot, index) => (
              <button
                key={index}
                onClick={() => slot.status === 'available' && onBookSlot(court.id, slot)}
                className={`p-2 rounded-lg text-sm font-medium transition-all duration-200 ${
                  slot.status === 'available'
                    ? selectedSlots.some(s => s.courtId === court.id && s.time === slot.time)
                      ? 'bg-blue-500 text-white'
                      : 'bg-green-50 text-green-700 hover:bg-green-100 cursor-pointer'
                    : 'bg-red-50 text-red-700 cursor-not-allowed'
                }`}
                disabled={slot.status !== 'available'}
              >
                {slot.time}
                <div className="text-xs mt-1">
                  {slot.status === 'available' ? '✅ Trống' : '❌ Đã đặt'}
                </div>
              </button>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
};

function BookingPage() {
  const [selectedDate, setSelectedDate] = useState('2025-07-24');
  const [selectedSlots, setSelectedSlots] = useState([]);
  const [showBookingSummary, setShowBookingSummary] = useState(false);

  const handleBookSlot = (courtId, slot) => {
    const slotData = {
      courtId,
      courtName: courts.find(c => c.id === courtId)?.name,
      time: slot.time,
      date: slot.date,
      price: courts.find(c => c.id === courtId)?.price
    };

    const existingIndex = selectedSlots.findIndex(
      s => s.courtId === courtId && s.time === slot.time
    );

    if (existingIndex > -1) {
      // Remove if already selected
      setSelectedSlots(selectedSlots.filter((_, index) => index !== existingIndex));
    } else {
      // Add new selection
      setSelectedSlots([...selectedSlots, slotData]);
    }
  };

  const totalAmount = selectedSlots.reduce((sum, slot) => sum + slot.price, 0);

  const handleConfirmBooking = () => {
    alert(`Đặt sân thành công! Tổng tiền: ${totalAmount.toLocaleString('vi-VN')}đ`);
    setSelectedSlots([]);
    setShowBookingSummary(false);
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 via-white to-purple-50">
      <Navigation />
      
      {/* Header */}
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        <div className="text-center mb-8">
          <h1 className="text-3xl md:text-4xl font-bold text-gray-800 mb-4">
            Đặt sân cầu lông
          </h1>
          <p className="text-lg text-gray-600">
            Chọn sân và khung giờ phù hợp với bạn
          </p>
        </div>

        {/* Date Selector */}
        <div className="bg-white rounded-lg shadow-md p-6 mb-8">
          <div className="flex items-center justify-between">
            <div>
              <h3 className="text-lg font-semibold text-gray-800">Chọn ngày:</h3>
              <input 
                type="date" 
                value={selectedDate}
                onChange={(e) => setSelectedDate(e.target.value)}
                className="mt-2 px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:border-blue-500"
                min={new Date().toISOString().split('T')[0]}
              />
            </div>
            
            {selectedSlots.length > 0 && (
              <div className="text-right">
                <div className="text-sm text-gray-600 mb-2">
                  Đã chọn: {selectedSlots.length} khung giờ
                </div>
                <div className="text-xl font-bold text-blue-600 mb-2">
                  Tổng: {totalAmount.toLocaleString('vi-VN')}đ
                </div>
                <button
                  onClick={() => setShowBookingSummary(true)}
                  className="bg-gradient-to-r from-green-500 to-green-600 hover:from-green-600 hover:to-green-700 text-white px-6 py-2 rounded-lg font-medium transition-all duration-200"
                >
                  Xem chi tiết & Thanh toán
                </button>
              </div>
            )}
          </div>
        </div>

        {/* Courts Grid */}
        <div className="grid grid-cols-1 lg:grid-cols-2 xl:grid-cols-3 gap-8">
          {courts.map((court) => (
            <CourtBookingCard
              key={court.id}
              court={court}
              onBookSlot={handleBookSlot}
              selectedSlots={selectedSlots}
            />
          ))}
        </div>
      </div>

      {/* Booking Summary Modal */}
      {showBookingSummary && (
        <div className="fixed inset-0 bg-black/50 flex items-center justify-center z-50 p-4">
          <div className="bg-white rounded-xl shadow-2xl max-w-2xl w-full max-h-[80vh] overflow-y-auto">
            <div className="p-6">
              <div className="flex items-center justify-between mb-6">
                <h2 className="text-2xl font-bold text-gray-800">Chi tiết đặt sân</h2>
                <button
                  onClick={() => setShowBookingSummary(false)}
                  className="text-gray-500 hover:text-gray-700 text-2xl"
                >
                  ✕
                </button>
              </div>

              <div className="space-y-4 mb-6">
                {selectedSlots.map((slot, index) => (
                  <div key={index} className="bg-gray-50 rounded-lg p-4">
                    <div className="flex justify-between items-start">
                      <div>
                        <h3 className="font-semibold text-gray-800">{slot.courtName}</h3>
                        <p className="text-gray-600">📅 {slot.date}</p>
                        <p className="text-gray-600">🕒 {slot.time}</p>
                      </div>
                      <div className="text-right">
                        <span className="text-lg font-bold text-blue-600">
                          {slot.price.toLocaleString('vi-VN')}đ
                        </span>
                      </div>
                    </div>
                  </div>
                ))}
              </div>

              <div className="border-t pt-4">
                <div className="flex justify-between items-center mb-6">
                  <span className="text-xl font-bold text-gray-800">Tổng cộng:</span>
                  <span className="text-2xl font-bold text-blue-600">
                    {totalAmount.toLocaleString('vi-VN')}đ
                  </span>
                </div>

                <div className="flex gap-4">
                  <button
                    onClick={() => setShowBookingSummary(false)}
                    className="flex-1 bg-gray-500 hover:bg-gray-600 text-white py-3 rounded-lg font-medium transition-all duration-200"
                  >
                    Quay lại
                  </button>
                  <button
                    onClick={handleConfirmBooking}
                    className="flex-1 bg-gradient-to-r from-green-500 to-green-600 hover:from-green-600 hover:to-green-700 text-white py-3 rounded-lg font-medium transition-all duration-200"
                  >
                    Xác nhận đặt sân
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}

export default BookingPage;