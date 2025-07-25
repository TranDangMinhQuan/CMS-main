import axios from 'axios';
import { User, Court, Booking, Review, LoginCredentials, RegisterData, BookingCreate } from '../types';

const API_BASE_URL = 'http://localhost:8080/api';

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Auth API
export const authAPI = {
  login: async (credentials: LoginCredentials) => {
    const response = await api.post('/auth/login', credentials);
    return response.data;
  },

  register: async (userData: RegisterData) => {
    const response = await api.post('/auth/register', userData);
    return response.data;
  },

  logout: async () => {
    const response = await api.post('/auth/logout');
    return response.data;
  },
};

// Courts API (Guest accessible)
export const courtsAPI = {
  // Public endpoints for guests
  getAllCourtsPublic: async (): Promise<Court[]> => {
    const response = await api.get('/courts/public');
    return response.data;
  },

  getCourtByIdPublic: async (id: number) => {
    const response = await api.get(`/courts/public/${id}`);
    return response.data;
  },

  getAvailableCourtsPublic: async (startTime: string, endTime: string): Promise<Court[]> => {
    const response = await api.get('/courts/public/available', {
      params: { startTime, endTime }
    });
    return response.data;
  },

  searchCourtsPublic: async (name: string): Promise<Court[]> => {
    const response = await api.get('/courts/public/search', {
      params: { name }
    });
    return response.data;
  },

  // Protected endpoints for authenticated users
  getAllCourts: async (): Promise<Court[]> => {
    const response = await api.get('/courts');
    return response.data;
  },

  getCourtById: async (id: number) => {
    const response = await api.get(`/courts/${id}`);
    return response.data;
  },

  createCourt: async (courtData: any) => {
    const response = await api.post('/courts', courtData);
    return response.data;
  },

  updateCourt: async (id: number, courtData: any) => {
    const response = await api.put(`/courts/${id}`, courtData);
    return response.data;
  },

  updateCourtStatus: async (id: number, status: string) => {
    const response = await api.put(`/courts/${id}/status`, { status });
    return response.data;
  },

  deleteCourt: async (id: number) => {
    const response = await api.delete(`/courts/${id}`);
    return response.data;
  },
};

// Reviews API (Public accessible)
export const reviewsAPI = {
  getReviewsByCourtId: async (courtId: number): Promise<Review[]> => {
    const response = await api.get(`/reviews/public/court/${courtId}`);
    return response.data;
  },

  createReview: async (reviewData: any) => {
    const response = await api.post('/reviews', reviewData);
    return response.data;
  },

  updateReview: async (id: number, reviewData: any) => {
    const response = await api.put(`/reviews/${id}`, reviewData);
    return response.data;
  },

  deleteReview: async (id: number) => {
    const response = await api.delete(`/reviews/${id}`);
    return response.data;
  },
};

// Bookings API
export const bookingsAPI = {
  createBooking: async (bookingData: BookingCreate) => {
    const response = await api.post('/bookings', bookingData);
    return response.data;
  },

  getMyBookings: async (): Promise<Booking[]> => {
    const response = await api.get('/bookings/my');
    return response.data;
  },

  getAllBookings: async (): Promise<Booking[]> => {
    const response = await api.get('/bookings');
    return response.data;
  },

  getBookingById: async (id: number): Promise<Booking> => {
    const response = await api.get(`/bookings/${id}`);
    return response.data;
  },

  updateBookingStatus: async (id: number, status: string) => {
    const response = await api.put(`/bookings/${id}/status`, { status });
    return response.data;
  },

  cancelBooking: async (id: number) => {
    const response = await api.delete(`/bookings/${id}`);
    return response.data;
  },
};

// Users API (Admin)
export const usersAPI = {
  getAllUsers: async (): Promise<User[]> => {
    const response = await api.get('/admin/users');
    return response.data;
  },

  getUserById: async (id: number): Promise<User> => {
    const response = await api.get(`/admin/users/${id}`);
    return response.data;
  },

  updateUserRole: async (id: number, role: string) => {
    const response = await api.put(`/admin/users/${id}/role`, { role });
    return response.data;
  },

  updateUserStatus: async (id: number, status: string) => {
    const response = await api.put(`/admin/users/${id}/status`, { status });
    return response.data;
  },
};

export default api;