import { Link } from "react-router-dom";

function Header() {
  return (
    <div className="flex items-center justify-between px-4 py-2 bg-gray-100">
      <div className="w-20 h-16">
        <img
          src="https://www.playgroundequipment.com/storage/image-gallery/cms-images/_badminton_1428046_1280.jpg"
          alt="logo"
          className="object-cover w-full h-full rounded"
        />
      </div>

      <div className="flex-1 px-4">
        <input
          type="text"
          placeholder="Search"
          className="w-full px-3 py-1 border rounded"
        />
      </div>

      <div className="p-2">
        <Link to="/login" className="p-2 hover:underline">Đăng Nhập</Link>
        <Link to="/signup" className="p-2 hover:underline">Đăng Ký</Link>
      </div>

      <div>
        <ul className="flex space-x-4">
          <li><Link to="/" className="hover:underline">Trang Chủ</Link></li>
          <li><Link to="/forgot" className="hover:underline">Quên Mật Khẩu</Link></li>
        </ul>
      </div>
    </div>
  );
}

export default Header;
