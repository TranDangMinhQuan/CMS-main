export interface User {
  id: number;
  username: string;
  email: string;
  fullName: string;
  phoneNumber?: string;
  role: 'GUEST' | 'MEMBER' | 'STAFF' | 'OWNER';
  status: 'ACTIVE' | 'SUSPENDED';
  createdAt: string;
  updatedAt: string;
}

export interface Court {
  id: number;
  courtName: string;
  description?: string;
  pricePerHour: number;
  status: 'AVAILABLE' | 'UNAVAILABLE' | 'MAINTENANCE';
  imageUrl?: string;
  createdAt: string;
  updatedAt: string;
}

export interface Booking {
  id: number;
  user: User;
  court: Court;
  startTime: string;
  endTime: string;
  totalAmount: number;
  status: 'PENDING' | 'CONFIRMED' | 'COMPLETED' | 'CANCELLED';
  paymentMethod?: 'ONLINE' | 'OFFLINE';
  notes?: string;
  createdAt: string;
  updatedAt: string;
}

export interface Review {
  id: number;
  user: User;
  court: Court;
  rating: number;
  comment?: string;
  createdAt: string;
  updatedAt: string;
}

export interface LoginCredentials {
  username: string;
  password: string;
}

export interface RegisterData {
  username: string;
  email: string;
  password: string;
  fullName: string;
  phoneNumber?: string;
}

export interface BookingCreate {
  courtId: number;
  startTime: string;
  endTime: string;
  paymentMethod?: 'ONLINE' | 'OFFLINE';
  notes?: string;
  couponCodes?: string[];
  racketQuantity?: number;
}