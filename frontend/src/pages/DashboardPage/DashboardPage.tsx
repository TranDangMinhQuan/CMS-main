import React, { useEffect, useState } from 'react';
import { reportAPI, bookingAPI } from '../../services/api';

const DashboardPage: React.FC = () => {
  const [dashboardData, setDashboardData] = useState<any>({});
  const [todayBookings, setTodayBookings] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchDashboardData();
  }, []);

  const fetchDashboardData = async () => {
    try {
      const [dashboard, bookings] = await Promise.all([
        reportAPI.getDashboardData(),
        bookingAPI.getTodayBookings()
      ]);
      
      setDashboardData(dashboard);
      setTodayBookings(bookings);
    } catch (error) {
      console.error('Error fetching dashboard data:', error);
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return <div className="loading"><div className="spinner"></div></div>;
  }

  return (
    <div className="dashboard-page">
      <div className="page-header">
        <h1>Dashboard</h1>
        <p>Staff & Owner Management Dashboard</p>
      </div>

      <div className="dashboard-stats grid grid-3">
        <div className="stat-card card">
          <h3>Today's Revenue</h3>
          <div className="stat-value">
            {dashboardData.todayRevenue?.toLocaleString() || '0'} VND
          </div>
        </div>
        
        <div className="stat-card card">
          <h3>Today's Bookings</h3>
          <div className="stat-value">
            {dashboardData.todayBookings || 0}
          </div>
        </div>
        
        <div className="stat-card card">
          <h3>Total Transactions</h3>
          <div className="stat-value">
            {dashboardData.todayTransactions?.length || 0}
          </div>
        </div>
      </div>

      <div className="dashboard-content">
        <div className="card">
          <h3>Today's Bookings</h3>
          {todayBookings.length === 0 ? (
            <p>No bookings for today.</p>
          ) : (
            <div className="bookings-table">
              <table className="table">
                <thead>
                  <tr>
                    <th>Court</th>
                    <th>Customer</th>
                    <th>Time</th>
                    <th>Status</th>
                    <th>Amount</th>
                  </tr>
                </thead>
                <tbody>
                  {todayBookings.map((booking: any) => (
                    <tr key={booking.id}>
                      <td>{booking.court?.name}</td>
                      <td>{booking.user?.fullName}</td>
                      <td>
                        {new Date(booking.startTime).toLocaleTimeString()} - 
                        {new Date(booking.endTime).toLocaleTimeString()}
                      </td>
                      <td>
                        <span className={`status ${booking.status.toLowerCase()}`}>
                          {booking.status}
                        </span>
                      </td>
                      <td>{booking.totalAmount?.toLocaleString()} VND</td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default DashboardPage;