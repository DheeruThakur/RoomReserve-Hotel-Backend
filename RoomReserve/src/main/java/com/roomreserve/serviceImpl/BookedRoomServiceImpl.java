package com.roomreserve.serviceImpl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.roomreserve.entities.BookedRoom;
import com.roomreserve.entities.Room;
import com.roomreserve.exception.InvalidBookingRequestException;
import com.roomreserve.exception.ResourceNotFoundException;
import com.roomreserve.repository.BookedRoomRepository;
import com.roomreserve.service.BookedRoomService;
import com.roomreserve.service.RoomService;

@Service
public class BookedRoomServiceImpl implements BookedRoomService{

	@Autowired
	private BookedRoomRepository bookedRoomRepository;
	
	@Autowired
	private RoomService roomService;
	
	@Override
	public List<BookedRoom> getAllBookingsByRoomId(Long id) {
		List<BookedRoom> bookings = bookedRoomRepository.findByRoomId(id);
		return bookings;
	}

	@Override
	public List<BookedRoom> getAllBookings() {
		List<BookedRoom> bookings = bookedRoomRepository.findAll();
		return bookings;
	}
	
	@Override
	public List<BookedRoom> getBookingsByUserEmail(String email) {
		List<BookedRoom> byGuestEmail = bookedRoomRepository.findByGuestEmail(email);
		return byGuestEmail;
	}

	@Override
	public BookedRoom getBookingByConfirmationCode(String confirmationCode) {
		return bookedRoomRepository.findByBookingConfirmationCode(confirmationCode).orElseThrow(() -> new ResourceNotFoundException("No booking found with booking code : "+confirmationCode));
	}

	@Override
	public String saveBooking(long roomId, BookedRoom bookingRequest) {
		
		if(bookingRequest.getCheckOutDate().isBefore(bookingRequest.getCheckInDate())) {
			throw new InvalidBookingRequestException("Check-in date must come before Check-out date");
		}
		
		Room room = roomService.getRoomById(roomId);
		List<BookedRoom> bookings = room.getBookings();
		
		boolean roomIsAvailable = roomIsAvailable(bookingRequest , bookings);
		
		if(roomIsAvailable) {
			room.addBooking(bookingRequest);
			bookedRoomRepository.save(bookingRequest);
		}
		else {
			throw new InvalidBookingRequestException("Sorry , This room is not available for selected dates;");
		}
		return bookingRequest.getBookingConfirmationCode();
	}

	@Override
	public void cancelBooking(long bookingId) {
		BookedRoom booking = bookedRoomRepository.findById(bookingId).orElseThrow(() -> new ResourceNotFoundException("Booking with this bookingId not found"));
		bookedRoomRepository.delete(booking);
	}
	
	private boolean roomIsAvailable(BookedRoom bookingRequest , List<BookedRoom> existingBookings) {
		return existingBookings.stream().noneMatch(existingBooking -> 
				bookingRequest.getCheckInDate().equals(existingBooking.getCheckInDate())
				
				|| bookingRequest.getCheckOutDate().isBefore(existingBooking.getCheckOutDate())
				
				|| (bookingRequest.getCheckInDate().isAfter(existingBooking.getCheckInDate()) && bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckOutDate()))
				
				|| (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate()) && bookingRequest.getCheckOutDate().equals(existingBooking.getCheckOutDate()))
				
				|| (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate()) && bookingRequest.getCheckOutDate().isAfter(existingBooking.getCheckOutDate()))
				
				|| (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate()) && bookingRequest.getCheckOutDate().equals(existingBooking.getCheckInDate()))
				
				|| (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate()) && bookingRequest.getCheckOutDate().equals(bookingRequest.getCheckInDate()))
			
		);
	}


}
