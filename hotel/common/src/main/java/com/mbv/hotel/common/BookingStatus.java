package com.mbv.hotel.common;

public enum BookingStatus {
	BOOK_ONLY,
	BOOKED,
	PAY_PENDING,
    PAY_PROCESSING,
    PAY_SUCCESS,
    PAY_ERROR,
    PAY_CANCELED,
    PAY_ONLY,
    UNKNOWN,
    PAYING_NOT_FOUND
}
