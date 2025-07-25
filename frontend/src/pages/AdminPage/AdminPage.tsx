import React, { useEffect, useState } from 'react';
import { User, Court } from '../../types';
import { userAPI, courtAPI } from '../../services/api';

const AdminPage: React.FC = () => {
  const [users, setUsers] = useState<User[]>([]);
  const [courts, setCourts] = useState<Court[]>([]);
  const [loading, setLoading] = useState(true);
  const [activeTab, setActiveTab] = useState('users');

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = async () => {
    try {
      const [usersData, courtsData] = await Promise.all([
        userAPI.getAllUsers(),
        courtAPI.getAllCourts()
      ]);
      
      setUsers(usersData);
      setCourts(courtsData);
    } catch (error) {
      console.error('Error fetching data:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleUserStatusUpdate = async (userId: number, isActive: boolean) => {
    try {
      await userAPI.updateUserStatus(userId, isActive);
      fetchData(); // Refresh data
    } catch (error) {
      console.error('Error updating user status:', error);
    }
  };

  const handleCourtStatusUpdate = async (courtId: number, status: string) => {
    try {
      await courtAPI.updateCourtStatus(courtId, status);
      fetchData(); // Refresh data
    } catch (error) {
      console.error('Error updating court status:', error);
    }
  };

  if (loading) {
    return <div className="loading"><div className="spinner"></div></div>;
  }

  return (
    <div className="admin-page">
      <div className="page-header">
        <h1>Admin Panel</h1>
        <p>Owner Management Dashboard</p>
      </div>

      <div className="admin-tabs">
        <button 
          className={`tab-btn ${activeTab === 'users' ? 'active' : ''}`}
          onClick={() => setActiveTab('users')}
        >
          User Management
        </button>
        <button 
          className={`tab-btn ${activeTab === 'courts' ? 'active' : ''}`}
          onClick={() => setActiveTab('courts')}
        >
          Court Management
        </button>
      </div>

      {activeTab === 'users' && (
        <div className="card">
          <h3>User Management</h3>
          <div className="table-container">
            <table className="table">
              <thead>
                <tr>
                  <th>Name</th>
                  <th>Username</th>
                  <th>Email</th>
                  <th>Role</th>
                  <th>Status</th>
                  <th>Actions</th>
                </tr>
              </thead>
              <tbody>
                {users.map((user) => (
                  <tr key={user.id}>
                    <td>{user.fullName}</td>
                    <td>{user.username}</td>
                    <td>{user.email}</td>
                    <td>
                      <span className={`role ${user.role.toLowerCase()}`}>
                        {user.role}
                      </span>
                    </td>
                    <td>
                      <span className={`status ${user.isActive ? 'active' : 'suspended'}`}>
                        {user.isActive ? 'Active' : 'Suspended'}
                      </span>
                    </td>
                    <td>
                      <button
                        onClick={() => handleUserStatusUpdate(user.id, !user.isActive)}
                        className={`btn btn-sm ${user.isActive ? 'btn-warning' : 'btn-success'}`}
                      >
                        {user.isActive ? 'Suspend' : 'Activate'}
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>
      )}

      {activeTab === 'courts' && (
        <div className="card">
          <h3>Court Management</h3>
          <div className="table-container">
            <table className="table">
              <thead>
                <tr>
                  <th>Court Name</th>
                  <th>Description</th>
                  <th>Hourly Rate</th>
                  <th>Status</th>
                  <th>Actions</th>
                </tr>
              </thead>
              <tbody>
                {courts.map((court) => (
                  <tr key={court.id}>
                    <td>{court.name}</td>
                    <td>{court.description}</td>
                    <td>{court.hourlyRate.toLocaleString()} VND</td>
                    <td>
                      <span className={`status ${court.status.toLowerCase()}`}>
                        {court.status}
                      </span>
                    </td>
                    <td>
                      <select
                        value={court.status}
                        onChange={(e) => handleCourtStatusUpdate(court.id, e.target.value)}
                        className="form-select"
                      >
                        <option value="AVAILABLE">Available</option>
                        <option value="UNAVAILABLE">Unavailable</option>
                        <option value="MAINTENANCE">Maintenance</option>
                      </select>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>
      )}
    </div>
  );
};

export default AdminPage;