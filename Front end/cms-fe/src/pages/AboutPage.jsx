import React from 'react';
import { Link } from 'react-router-dom';

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
          <Link to="/history" className="px-4 py-2 rounded-lg font-medium text-gray-600 hover:text-blue-600 transition-all duration-200">
            📋 Lịch sử
          </Link>
          <Link to="/about" className="px-4 py-2 rounded-lg font-medium bg-gradient-to-r from-blue-500 to-purple-600 text-white">
            ℹ️ Về chúng tôi
          </Link>
        </div>
      </div>
    </div>
  </nav>
);

const TeamMember = ({ name, role, image, description }) => (
  <div className="bg-white rounded-xl shadow-lg p-6 text-center hover:shadow-xl transition-all duration-300">
    <div className="w-24 h-24 bg-gradient-to-r from-blue-500 to-purple-600 rounded-full flex items-center justify-center mx-auto mb-4">
      <span className="text-3xl text-white">{image}</span>
    </div>
    <h3 className="text-xl font-bold text-gray-800 mb-2">{name}</h3>
    <p className="text-blue-600 font-medium mb-3">{role}</p>
    <p className="text-gray-600 text-sm">{description}</p>
  </div>
);

const FeatureCard = ({ icon, title, description }) => (
  <div className="bg-white rounded-xl shadow-lg p-6 hover:shadow-xl transition-all duration-300">
    <div className="bg-gradient-to-r from-blue-100 to-purple-100 w-16 h-16 rounded-full flex items-center justify-center mb-4">
      <span className="text-3xl">{icon}</span>
    </div>
    <h3 className="text-xl font-bold text-gray-800 mb-3">{title}</h3>
    <p className="text-gray-600">{description}</p>
  </div>
);

function AboutPage() {
  const teamMembers = [
    {
      name: "Nguyễn Chí Bảo",
      role: "Founder & CEO",
      image: "👨‍💼",
      description: "Người sáng lập với tình yêu cầu lông và công nghệ. 10+ năm kinh nghiệm trong lĩnh vực thể thao."
    },
    {
      name: "Trần Minh Anh",
      role: "CTO",
      image: "👨‍💻",
      description: "Chuyên gia công nghệ với kinh nghiệm phát triển các ứng dụng mobile và web quy mô lớn."
    },
    {
      name: "Lê Thị Hồng",
      role: "Operations Manager",
      image: "👩‍💼",
      description: "Quản lý vận hành với 8 năm kinh nghiệm trong ngành dịch vụ và quản lý sân thể thao."
    }
  ];

  const features = [
    {
      icon: "🎯",
      title: "Sứ mệnh",
      description: "Kết nối cộng đồng cầu lông Việt Nam thông qua nền tảng đặt sân hiện đại, tiện lợi và đáng tin cậy."
    },
    {
      icon: "👁️",
      title: "Tầm nhìn",
      description: "Trở thành nền tảng đặt sân thể thao số 1 Việt Nam, mang lại trải nghiệm tuyệt vời cho người chơi."
    },
    {
      icon: "💎",
      title: "Giá trị cốt lõi",
      description: "Chất lượng, minh bạch, tiện lợi và luôn đặt khách hàng làm trung tâm trong mọi hoạt động."
    }
  ];

  const milestones = [
    { year: "2023", event: "Thành lập công ty và phát triển MVP" },
    { year: "2024", event: "Ra mắt ứng dụng với 20+ sân cầu lông đối tác" },
    { year: "2024", event: "Đạt 1000+ người dùng và mở rộng ra 5 quận tại TP.HCM" },
    { year: "2025", event: "Mở rộng ra Hà Nội và các tỉnh thành lớn" }
  ];

  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 via-white to-purple-50">
      <Navigation />
      
      {/* Hero Section */}
      <div className="relative overflow-hidden bg-gradient-to-r from-blue-600 to-purple-600 text-white">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-20">
          <div className="text-center">
            <h1 className="text-4xl md:text-6xl font-bold mb-6">
              Về BadmintonBooking
            </h1>
            <p className="text-xl md:text-2xl mb-8 max-w-3xl mx-auto">
              Chúng tôi là đội ngũ đam mê cầu lông, cam kết mang đến trải nghiệm đặt sân tuyệt vời nhất cho cộng đồng người chơi Việt Nam.
            </p>
            <div className="text-6xl mb-4">🏸</div>
          </div>
        </div>
      </div>

      {/* Mission, Vision, Values */}
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-16">
        <div className="text-center mb-12">
          <h2 className="text-3xl md:text-4xl font-bold text-gray-800 mb-4">
            Sứ mệnh & Tầm nhìn
          </h2>
          <p className="text-lg text-gray-600">
            Những giá trị định hướng mọi hoạt động của chúng tôi
          </p>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-3 gap-8 mb-16">
          {features.map((feature, index) => (
            <FeatureCard key={index} {...feature} />
          ))}
        </div>
      </div>

      {/* Company Story */}
      <div className="bg-gradient-to-r from-gray-50 to-blue-50 py-16">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="grid grid-cols-1 lg:grid-cols-2 gap-12 items-center">
            <div>
              <h2 className="text-3xl md:text-4xl font-bold text-gray-800 mb-6">
                Câu chuyện của chúng tôi
              </h2>
              <div className="space-y-4 text-gray-600">
                <p>
                  BadmintonBooking ra đời từ một nhu cầu thực tế: việc đặt sân cầu lông truyền thống thường gặp nhiều khó khăn như không biết sân nào trống, giá cả không minh bạch, và quy trình đặt sân phức tạp.
                </p>
                <p>
                  Với đội ngũ có nhiều năm kinh nghiệm trong lĩnh vực công nghệ và thể thao, chúng tôi quyết định xây dựng một nền tảng kết nối người chơi với các sân cầu lông một cách dễ dàng và hiệu quả nhất.
                </p>
                <p>
                  Từ những ngày đầu khởi nghiệp với 5 sân đối tác, đến nay chúng tôi đã phục vụ hàng nghìn lượt đặt sân và trở thành sự lựa chọn tin cậy của cộng đồng cầu lông Việt Nam.
                </p>
              </div>
            </div>
            <div className="bg-white rounded-xl shadow-lg p-8">
              <h3 className="text-2xl font-bold text-gray-800 mb-6 text-center">Cột mốc phát triển</h3>
              <div className="space-y-4">
                {milestones.map((milestone, index) => (
                  <div key={index} className="flex items-start gap-4">
                    <div className="bg-blue-500 text-white w-12 h-12 rounded-full flex items-center justify-center font-bold text-sm">
                      {milestone.year}
                    </div>
                    <div className="flex-1 pt-2">
                      <p className="text-gray-700">{milestone.event}</p>
                    </div>
                  </div>
                ))}
              </div>
            </div>
          </div>
        </div>
      </div>

      {/* Team Section */}
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-16">
        <div className="text-center mb-12">
          <h2 className="text-3xl md:text-4xl font-bold text-gray-800 mb-4">
            Đội ngũ của chúng tôi
          </h2>
          <p className="text-lg text-gray-600">
            Những người đứng sau thành công của BadmintonBooking
          </p>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-3 gap-8 mb-16">
          {teamMembers.map((member, index) => (
            <TeamMember key={index} {...member} />
          ))}
        </div>
      </div>

      {/* Stats Section */}
      <div className="bg-gradient-to-r from-blue-600 to-purple-600 py-16">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="text-center mb-12">
            <h2 className="text-3xl md:text-4xl font-bold text-white mb-4">
              Con số ấn tượng
            </h2>
            <p className="text-xl text-blue-100">
              Những thành tựu chúng tôi đã đạt được
            </p>
          </div>
          
          <div className="grid grid-cols-2 md:grid-cols-4 gap-8">
            <div className="text-center text-white">
              <div className="text-4xl md:text-5xl font-bold mb-2">50+</div>
              <p className="text-blue-100">Sân đối tác</p>
            </div>
            <div className="text-center text-white">
              <div className="text-4xl md:text-5xl font-bold mb-2">5000+</div>
              <p className="text-blue-100">Lượt đặt sân</p>
            </div>
            <div className="text-center text-white">
              <div className="text-4xl md:text-5xl font-bold mb-2">1500+</div>
              <p className="text-blue-100">Người dùng</p>
            </div>
            <div className="text-center text-white">
              <div className="text-4xl md:text-5xl font-bold mb-2">4.8⭐</div>
              <p className="text-blue-100">Đánh giá</p>
            </div>
          </div>
        </div>
      </div>

      {/* Contact Section */}
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-16">
        <div className="text-center mb-12">
          <h2 className="text-3xl md:text-4xl font-bold text-gray-800 mb-4">
            Liên hệ với chúng tôi
          </h2>
          <p className="text-lg text-gray-600">
            Chúng tôi luôn sẵn sàng lắng nghe và hỗ trợ bạn
          </p>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-8">
          <div className="bg-white rounded-xl shadow-lg p-6 text-center">
            <div className="bg-blue-100 w-16 h-16 rounded-full flex items-center justify-center mx-auto mb-4">
              <span className="text-2xl">📍</span>
            </div>
            <h3 className="font-bold text-gray-800 mb-2">Địa chỉ</h3>
            <p className="text-gray-600 text-sm">123 Nguyễn Huệ, Quận 1, TP.HCM</p>
          </div>
          
          <div className="bg-white rounded-xl shadow-lg p-6 text-center">
            <div className="bg-green-100 w-16 h-16 rounded-full flex items-center justify-center mx-auto mb-4">
              <span className="text-2xl">📞</span>
            </div>
            <h3 className="font-bold text-gray-800 mb-2">Hotline</h3>
            <p className="text-gray-600 text-sm">0123 456 789</p>
          </div>
          
          <div className="bg-white rounded-xl shadow-lg p-6 text-center">
            <div className="bg-purple-100 w-16 h-16 rounded-full flex items-center justify-center mx-auto mb-4">
              <span className="text-2xl">📧</span>
            </div>
            <h3 className="font-bold text-gray-800 mb-2">Email</h3>
            <p className="text-gray-600 text-sm">support@badmintonbooking.vn</p>
          </div>
          
          <div className="bg-white rounded-xl shadow-lg p-6 text-center">
            <div className="bg-yellow-100 w-16 h-16 rounded-full flex items-center justify-center mx-auto mb-4">
              <span className="text-2xl">🕒</span>
            </div>
            <h3 className="font-bold text-gray-800 mb-2">Giờ làm việc</h3>
            <p className="text-gray-600 text-sm">8:00 - 22:00 (T2-CN)</p>
          </div>
        </div>
      </div>

      {/* CTA Section */}
      <div className="bg-gradient-to-r from-blue-600 to-purple-600 py-16">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 text-center">
          <h2 className="text-3xl md:text-4xl font-bold text-white mb-4">
            Sẵn sàng bắt đầu?
          </h2>
          <p className="text-xl text-blue-100 mb-8">
            Hãy đặt sân đầu tiên của bạn ngay hôm nay!
          </p>
          <Link
            to="/booking"
            className="inline-block bg-white text-blue-600 px-8 py-4 rounded-lg font-bold text-lg hover:bg-gray-100 transition-all duration-200 transform hover:scale-105"
          >
            Đặt sân ngay 🏸
          </Link>
        </div>
      </div>
    </div>
  );
}

export default AboutPage;