import React from 'react';
import { Link } from 'react-router-dom';

function OwnerDashboardPage() {
  return (
    <div className="min-h-screen bg-gray-100 py-10 px-4">
      <div className="max-w-5xl mx-auto">
        <header className="flex justify-between items-center mb-8">
          <h1 className="text-3xl font-bold text-gray-800">🎾 Bảng điều khiển của Chủ sân</h1>
          <Link
            to="/"
            className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700"
          >
            ⬅️ Quay lại Trang chủ
          </Link>
        </header>

        <section className="grid grid-cols-1 md:grid-cols-2 gap-6">
          <div className="bg-white rounded-lg shadow-md p-6">
            <h2 className="text-xl font-semibold mb-2">📋 Danh sách sân của bạn</h2>
            <p className="text-gray-600">Quản lý, chỉnh sửa thông tin và trạng thái hoạt động của các sân bạn sở hữu.</p>
            <button className="mt-4 px-4 py-2 bg-green-600 text-white rounded hover:bg-green-700">+ Thêm sân mới</button>
          </div>

          <div className="bg-white rounded-lg shadow-md p-6">
            <h2 className="text-xl font-semibold mb-2">📅 Quản lý đặt sân</h2>
            <p className="text-gray-600">Theo dõi các lượt đặt sân, xác nhận và điều chỉnh lịch trình cho phù hợp.</p>
            <button className="mt-4 px-4 py-2 bg-indigo-600 text-white rounded hover:bg-indigo-700">Xem lịch đặt</button>
          </div>

          <div className="bg-white rounded-lg shadow-md p-6">
            <h2 className="text-xl font-semibold mb-2">📊 Thống kê & Doanh thu</h2>
            <p className="text-gray-600">Xem tổng quan lượt đặt sân, doanh thu theo ngày/tháng.</p>
            <button className="mt-4 px-4 py-2 bg-yellow-500 text-white rounded hover:bg-yellow-600">Xem thống kê</button>
          </div>

          <div className="bg-white rounded-lg shadow-md p-6">
            <h2 className="text-xl font-semibold mb-2">⚙️ Cài đặt tài khoản</h2>
            <p className="text-gray-600">Cập nhật thông tin cá nhân, mật khẩu và liên hệ hỗ trợ.</p>
            <button className="mt-4 px-4 py-2 bg-gray-600 text-white rounded hover:bg-gray-700">Cập nhật thông tin</button>
          </div>
        </section>
      </div>
    </div>
  );
}

export default OwnerDashboardPage;
