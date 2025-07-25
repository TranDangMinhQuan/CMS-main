export interface User {
  id: number;
  username: string;
  email: string;
  fullName: string;
  phoneNumber?: string;
  role: UserRole;
  isActive: boolean;
  createdAt: string;
  updatedAt: string;
}

export interface Court {
  id: number;
  name: string;
  description?: string;
  hourlyRate: number;
  status: CourtStatus;
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
  status: BookingStatus;
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
}

export interface Payment {
  id: number;
  booking: Booking;
  amount: number;
  paymentMethod: PaymentMethod;
  status: PaymentStatus;
  transactionId?: string;
  paidAt?: string;
  createdAt: string;
}

export interface AuthResponse {
  token: string;
  username: string;
  email: string;
  fullName: string;
  role: UserRole;
}

export interface LoginRequest {
  username: string;
  password: string;
}

export interface RegisterRequest {
  username: string;
  email: string;
  password: string;
  fullName: string;
  phoneNumber?: string;
}

export interface BookingRequest {
  courtId: number;
  startTime: string;
  endTime: string;
  notes?: string;
  couponCode?: string;
  racketQuantity?: number;
  paymentMethod: PaymentMethod;
}

export enum UserRole {
  GUEST = 'GUEST',
  MEMBER = 'MEMBER',
  STAFF = 'STAFF',
  OWNER = 'OWNER'
}

export enum CourtStatus {
  AVAILABLE = 'AVAILABLE',
  UNAVAILABLE = 'UNAVAILABLE',
  MAINTENANCE = 'MAINTENANCE'
}

export enum BookingStatus {
  PENDING = 'PENDING',
  CONFIRMED = 'CONFIRMED',
  COMPLETED = 'COMPLETED',
  CANCELLED = 'CANCELLED'
}

export enum PaymentMethod {
  ONLINE = 'ONLINE',
  CASH = 'CASH'
}

export enum PaymentStatus {
  PENDING = 'PENDING',
  PAID = 'PAID',
  FAILED = 'FAILED',
  REFUNDED = 'REFUNDED'
}