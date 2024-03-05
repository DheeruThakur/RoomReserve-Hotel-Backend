package com.roomreserve.service;

import java.util.List;

import com.roomreserve.entities.BookedRoom;

public interface BookedRoomService {

	public List<BookedRoom> getAllBookingsByRoomId(Long id);

	public List<BookedRoom> getAllBookings();

	public BookedRoom getBookingByConfirmationCode(String confirmationCode);

	public String saveBooking(long roomId, BookedRoom requestBody);

	public void cancelBooking(long bookingId);

	public List<BookedRoom> getBookingsByUserEmail(String email);
}
