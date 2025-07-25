import React, { useEffect, useState } from 'react';
import { useNavigate, useSearchParams } from 'react-router-dom';
import { Court, PaymentMethod } from '../../types';
import { courtAPI, bookingAPI } from '../../services/api';

const BookingPage: React.FC = () => {
  const [searchParams] = useSearchParams();
  const navigate = useNavigate();
  const [courts, setCourts] = useState<Court[]>([]);
  const [selectedCourtId, setSelectedCourtId] = useState<number>(0);
  const [startTime, setStartTime] = useState('');
  const [endTime, setEndTime] = useState('');
  const [notes, setNotes] = useState('');
  const [couponCode, setCouponCode] = useState('');
  const [racketQuantity, setRacketQuantity] = useState(0);
  const [paymentMethod, setPaymentMethod] = useState<PaymentMethod>(PaymentMethod.CASH);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  useEffect(() => {
    fetchCourts();
    const courtId = searchParams.get('courtId');
    if (courtId) {
      setSelectedCourtId(parseInt(courtId));
    }
  }, [searchParams]);

  const fetchCourts = async () => {
    try {
      const courtsData = await courtAPI.getAvailableCourts();
      setCourts(courtsData);
    } catch (error) {
      console.error('Error fetching courts:', error);
    }
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError('');
    setLoading(true);

    try {
      const bookingData = {
        courtId: selectedCourtId,
        startTime,
        endTime,
        notes,
        couponCode,
        racketQuantity,
        paymentMethod
      };

      await bookingAPI.createBooking(bookingData);
      navigate('/my-bookings');
    } catch (error) {
      setError('Failed to create booking. Please try again.');
    } finally {
      setLoading(false);
    }
  };

  const calculateTotal = () => {
    const court = courts.find(c => c.id === selectedCourtId);
    if (!court || !startTime || !endTime) return 0;

    const start = new Date(startTime);
    const end = new Date(endTime);
    const hours = (end.getTime() - start.getTime()) / (1000 * 60 * 60);
    
    const courtCost = court.hourlyRate * hours;
    const racketCost = racketQuantity * 30000;
    
    return courtCost + racketCost;
  };

  return (
    <div className="booking-page">
      <div className="page-header">
        <h1>Book a Court</h1>
        <p>Reserve your badminton court for the perfect game</p>
      </div>

      {error && <div className="error-message">{error}</div>}

      <form onSubmit={handleSubmit} className="booking-form">
        <div className="form-section">
          <h3>Court Selection</h3>
          <div className="form-group">
            <label className="form-label">Select Court</label>
            <select
              className="form-select"
              value={selectedCourtId}
              onChange={(e) => setSelectedCourtId(parseInt(e.target.value))}
              required
            >
              <option value={0}>Choose a court...</option>
              {courts.map((court) => (
                <option key={court.id} value={court.id}>
                  {court.name} - {court.hourlyRate.toLocaleString()} VND/hour
                </option>
              ))}
            </select>
          </div>
        </div>

        <div className="form-section">
          <h3>Time & Date</h3>
          <div className="form-row">
            <div className="form-group">
              <label className="form-label">Start Time</label>
              <input
                type="datetime-local"
                className="form-input"
                value={startTime}
                onChange={(e) => setStartTime(e.target.value)}
                required
              />
            </div>
            <div className="form-group">
              <label className="form-label">End Time</label>
              <input
                type="datetime-local"
                className="form-input"
                value={endTime}
                onChange={(e) => setEndTime(e.target.value)}
                required
              />
            </div>
          </div>
        </div>

        <div className="form-section">
          <h3>Additional Options</h3>
          <div className="form-group">
            <label className="form-label">Racket Rental (30,000 VND each)</label>
            <input
              type="number"
              className="form-input"
              value={racketQuantity}
              onChange={(e) => setRacketQuantity(parseInt(e.target.value) || 0)}
              min="0"
              max="10"
            />
          </div>
          
          <div className="form-group">
            <label className="form-label">Coupon Code (optional)</label>
            <input
              type="text"
              className="form-input"
              value={couponCode}
              onChange={(e) => setCouponCode(e.target.value)}
              placeholder="Enter coupon code"
            />
          </div>

          <div className="form-group">
            <label className="form-label">Payment Method</label>
            <select
              className="form-select"
              value={paymentMethod}
              onChange={(e) => setPaymentMethod(e.target.value as PaymentMethod)}
            >
              <option value={PaymentMethod.CASH}>Pay at Court</option>
              <option value={PaymentMethod.ONLINE}>Pay Online</option>
            </select>
          </div>

          <div className="form-group">
            <label className="form-label">Notes (optional)</label>
            <textarea
              className="form-textarea"
              value={notes}
              onChange={(e) => setNotes(e.target.value)}
              placeholder="Any special requests or notes"
            />
          </div>
        </div>

        <div className="booking-summary">
          <h3>Booking Summary</h3>
          <div className="summary-item">
            <span>Total Amount:</span>
            <strong>{calculateTotal().toLocaleString()} VND</strong>
          </div>
        </div>

        <button type="submit" className="btn btn-primary booking-btn" disabled={loading}>
          {loading ? 'Creating Booking...' : 'Book Court'}
        </button>
      </form>
    </div>
  );
};

export default BookingPage;