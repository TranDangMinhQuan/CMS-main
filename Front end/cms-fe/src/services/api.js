const API_BASE_URL = 'http://localhost:8080/api';

// Court API
export const courtAPI = {
  getAllCourts: async () => {
    const response = await fetch(`${API_BASE_URL}/courts`);
    if (!response.ok) throw new Error('Failed to fetch courts');
    return response.json();
  },

  getCourtById: async (id) => {
    const response = await fetch(`${API_BASE_URL}/courts/${id}`);
    if (!response.ok) throw new Error('Failed to fetch court');
    return response.json();
  },

  searchCourts: async (keyword, page = 0, size = 10) => {
    const response = await fetch(`${API_BASE_URL}/courts/search?keyword=${keyword}&page=${page}&size=${size}`);
    if (!response.ok) throw new Error('Failed to search courts');
    return response.json();
  },

  filterCourts: async (filters, page = 0, size = 10) => {
    const params = new URLSearchParams();
    params.append('page', page);
    params.append('size', size);
    
    if (filters.district) params.append('district', filters.district);
    if (filters.city) params.append('city', filters.city);
    if (filters.minPrice) params.append('minPrice', filters.minPrice);
    if (filters.maxPrice) params.append('maxPrice', filters.maxPrice);

    const response = await fetch(`${API_BASE_URL}/courts/filter?${params}`);
    if (!response.ok) throw new Error('Failed to filter courts');
    return response.json();
  },

  getTopRatedCourts: async (limit = 5) => {
    const response = await fetch(`${API_BASE_URL}/courts/top-rated?limit=${limit}`);
    if (!response.ok) throw new Error('Failed to fetch top rated courts');
    return response.json();
  },

  getAvailableTimeSlots: async (courtId, date) => {
    const response = await fetch(`${API_BASE_URL}/bookings/available-slots?courtId=${courtId}&date=${date}`);
    if (!response.ok) throw new Error('Failed to fetch available slots');
    return response.json();
  }
};

// Booking API
export const bookingAPI = {
  createBooking: async (bookingData, token = null) => {
    const headers = {
      'Content-Type': 'application/json',
    };
    
    if (token) {
      headers['Authorization'] = `Bearer ${token}`;
    }

    const endpoint = token ? 
      `${API_BASE_URL}/bookings` : 
      `${API_BASE_URL}/bookings/guest`;

    const response = await fetch(endpoint, {
      method: 'POST',
      headers,
      body: JSON.stringify(bookingData)
    });

    if (!response.ok) throw new Error('Failed to create booking');
    return response.json();
  },

  getBookingByCode: async (bookingCode) => {
    const response = await fetch(`${API_BASE_URL}/bookings/code/${bookingCode}`);
    if (!response.ok) throw new Error('Failed to fetch booking');
    return response.json();
  },

  getMyBookings: async (token) => {
    const response = await fetch(`${API_BASE_URL}/bookings/my-bookings`, {
      headers: {
        'Authorization': `Bearer ${token}`
      }
    });
    if (!response.ok) throw new Error('Failed to fetch bookings');
    return response.json();
  },

  confirmBooking: async (bookingId, token) => {
    const response = await fetch(`${API_BASE_URL}/bookings/${bookingId}/confirm`, {
      method: 'PUT',
      headers: {
        'Authorization': `Bearer ${token}`
      }
    });
    if (!response.ok) throw new Error('Failed to confirm booking');
    return response.json();
  },

  cancelBooking: async (bookingId, reason = null, token = null) => {
    const url = new URL(`${API_BASE_URL}/bookings/${bookingId}/cancel`);
    if (reason) url.searchParams.append('reason', reason);

    const headers = {};
    if (token) headers['Authorization'] = `Bearer ${token}`;

    const response = await fetch(url, {
      method: 'PUT',
      headers
    });
    if (!response.ok) throw new Error('Failed to cancel booking');
    return response.json();
  }
};

// Auth API
export const authAPI = {
  login: async (email, password) => {
    const response = await fetch(`${API_BASE_URL}/auth/login`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ email, password })
    });
    if (!response.ok) throw new Error('Login failed');
    return response.json();
  },

  register: async (userData) => {
    const response = await fetch(`${API_BASE_URL}/auth/register`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(userData)
    });
    if (!response.ok) throw new Error('Registration failed');
    return response.json();
  },

  forgotPassword: async (email) => {
    const response = await fetch(`${API_BASE_URL}/auth/forgot-password`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ email })
    });
    if (!response.ok) throw new Error('Forgot password failed');
    return response.json();
  }
};