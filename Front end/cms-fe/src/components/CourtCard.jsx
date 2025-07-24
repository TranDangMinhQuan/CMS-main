// src/components/CourtCard.jsx
function CourtCard({ name, image, price }) {
    return (
      <div className="bg-white rounded-xl shadow hover:shadow-lg transition p-4">
        <img
          src={image}
          alt={name}
          className="w-full h-40 object-cover rounded mb-3"
        />
        <h2 className="text-lg font-semibold">{name}</h2>
        <p className="text-green-600 font-bold mt-1">{price} VND/giờ</p>
      </div>
    );
  }
  
  export default CourtCard;
  