import axios from 'axios';
import { AuthResponse, LoginRequest, RegisterRequest, Court, Booking, BookingRequest, Review, User } from '../types';

const API_BASE_URL = 'http://localhost:8080/api';

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Add auth token to requests
api.interceptors.request.use((config) => {
  const token = localStorage.getItem('token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

// Auth API
export const authAPI = {
  login: (credentials: LoginRequest): Promise<AuthResponse> =>
    api.post('/auth/login', credentials).then(res => res.data),
  
  register: (userData: RegisterRequest): Promise<AuthResponse> =>
    api.post('/auth/register', userData).then(res => res.data),
};

// Court API
export const courtAPI = {
  getAllCourts: (): Promise<Court[]> =>
    api.get('/courts').then(res => res.data),
  
  getCourtById: (id: number): Promise<Court> =>
    api.get(`/courts/${id}`).then(res => res.data),
  
  getAvailableCourts: (startTime?: string, endTime?: string): Promise<Court[]> => {
    let url = '/courts/available';
    if (startTime && endTime) {
      url += `?startTime=${startTime}&endTime=${endTime}`;
    }
    return api.get(url).then(res => res.data);
  },
  
  createCourt: (court: Partial<Court>): Promise<Court> =>
    api.post('/courts', court).then(res => res.data),
  
  updateCourt: (id: number, court: Partial<Court>): Promise<Court> =>
    api.put(`/courts/${id}`, court).then(res => res.data),
  
  updateCourtStatus: (id: number, status: string): Promise<Court> =>
    api.put(`/courts/${id}/status?status=${status}`).then(res => res.data),
  
  deleteCourt: (id: number): Promise<void> =>
    api.delete(`/courts/${id}`).then(res => res.data),
};

// Booking API
export const bookingAPI = {
  createBooking: (bookingData: BookingRequest): Promise<Booking> =>
    api.post('/bookings', bookingData).then(res => res.data),
  
  getUserBookings: (): Promise<Booking[]> =>
    api.get('/bookings/my-bookings').then(res => res.data),
  
  getAllBookings: (): Promise<Booking[]> =>
    api.get('/bookings').then(res => res.data),
  
  getBookingById: (id: number): Promise<Booking> =>
    api.get(`/bookings/${id}`).then(res => res.data),
  
  updateBookingStatus: (id: number, status: string): Promise<Booking> =>
    api.put(`/bookings/${id}/status?status=${status}`).then(res => res.data),
  
  getTodayBookings: (): Promise<Booking[]> =>
    api.get('/bookings/today').then(res => res.data),
  
  getMonthlyBookings: (month: number, year: number): Promise<Booking[]> =>
    api.get(`/bookings/monthly?month=${month}&year=${year}`).then(res => res.data),
};

// Review API
export const reviewAPI = {
  createReview: (review: Partial<Review>): Promise<Review> =>
    api.post('/reviews', review).then(res => res.data),
  
  getReviewsByCourt: (courtId: number): Promise<Review[]> =>
    api.get(`/reviews/court/${courtId}`).then(res => res.data),
  
  getAverageRating: (courtId: number): Promise<number> =>
    api.get(`/reviews/court/${courtId}/average`).then(res => res.data),
  
  getUserReviews: (): Promise<Review[]> =>
    api.get('/reviews/my-reviews').then(res => res.data),
  
  deleteReview: (id: number): Promise<void> =>
    api.delete(`/reviews/${id}`).then(res => res.data),
};

// User API (for admin functions)
export const userAPI = {
  getAllUsers: (): Promise<User[]> =>
    api.get('/users').then(res => res.data),
  
  getActiveStaff: (): Promise<User[]> =>
    api.get('/users/staff').then(res => res.data),
  
  getActiveMembers: (): Promise<User[]> =>
    api.get('/users/members').then(res => res.data),
  
  createStaff: (userData: RegisterRequest): Promise<User> =>
    api.post('/users/staff', userData).then(res => res.data),
  
  updateUserStatus: (userId: number, isActive: boolean): Promise<User> =>
    api.put(`/users/${userId}/status?isActive=${isActive}`).then(res => res.data),
  
  updateUserRole: (userId: number, role: string): Promise<User> =>
    api.put(`/users/${userId}/role?role=${role}`).then(res => res.data),
};

// Reports API
export const reportAPI = {
  getTodayRevenue: (): Promise<number> =>
    api.get('/reports/revenue/today').then(res => res.data),
  
  getMonthlyRevenue: (month: number, year: number): Promise<number> =>
    api.get(`/reports/revenue/monthly?month=${month}&year=${year}`).then(res => res.data),
  
  getTodayTransactions: (): Promise<any[]> =>
    api.get('/reports/transactions/today').then(res => res.data),
  
  getTodayBookings: (): Promise<Booking[]> =>
    api.get('/reports/bookings/today').then(res => res.data),
  
  getDashboardData: (): Promise<any> =>
    api.get('/reports/dashboard').then(res => res.data),
};

export default api;