import React from "react";
import { Link } from "react-router-dom";

const Navigation = () => (
  <nav className="bg-white/80 backdrop-blur-md shadow-md sticky top-0 z-50">
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
      <div className="flex justify-between items-center h-16">
        <Link to="/" className="flex items-center">
          <div className="bg-gradient-to-r from-blue-500 to-purple-600 text-white p-2 rounded-lg mr-3">🏸</div>
          <h1 className="text-xl font-bold bg-gradient-to-r from-blue-600 to-purple-600 bg-clip-text text-transparent">
            BadmintonBooking
          </h1>
        </Link>
        <div className="flex space-x-1">
          <Link to="/" className="px-4 py-2 rounded-lg font-medium text-gray-600 hover:text-blue-600 transition">
            🏠 Trang chủ
          </Link>
          <Link to="/booking" className="px-4 py-2 rounded-lg font-medium text-gray-600 hover:text-blue-600 transition">
            📅 Đặt sân
          </Link>
          <Link to="/about" className="px-4 py-2 rounded-lg font-medium text-gray-600 hover:text-blue-600 transition">
            ℹ️ Về chúng tôi
          </Link>
        </div>
      </div>
    </div>
  </nav>
);

function LoginPage() {
  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 via-white to-purple-50">
      <Navigation />

      <div className="flex items-center justify-center py-20 px-4">
        <div className="bg-white shadow-xl rounded-xl p-8 w-full max-w-sm space-y-6">
          <h2 className="text-2xl font-bold text-center text-gray-800">Đăng Nhập</h2>

          <div>
            <label className="block text-sm font-medium text-gray-700 mb-1">Email:</label>
            <input
              type="email"
              placeholder="Nhập email"
              className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-400"
            />
          </div>

          <div>
            <label className="block text-sm font-medium text-gray-700 mb-1">Mật khẩu:</label>
            <input
              type="password"
              placeholder="Nhập mật khẩu"
              className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-400"
            />
          </div>

          <button className="w-full bg-gradient-to-r from-blue-500 to-purple-600 text-white font-semibold py-2 rounded-lg hover:opacity-90 transition">
            Đăng Nhập
          </button>

          <div className="flex justify-between text-sm text-gray-600">
            <Link to="/forgot-password" className="hover:underline text-blue-600">Quên mật khẩu?</Link>
            <span>
              Chưa có tài khoản?{" "}
              <Link to="/register" className="text-blue-600 hover:underline">
                Đăng ký
              </Link>
            </span>
          </div>
        </div>
      </div>
    </div>
  );
}

export default LoginPage;
