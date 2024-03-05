package com.roomreserve.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.roomreserve.entities.BookedRoom;
import com.roomreserve.entities.Room;
import com.roomreserve.exception.InvalidBookingRequestException;
import com.roomreserve.exception.ResourceNotFoundException;
import com.roomreserve.response.BookingResponse;
import com.roomreserve.response.RoomResponse;
import com.roomreserve.service.BookedRoomService;
import com.roomreserve.service.RoomService;

@RestController
@RequestMapping("/bookings")
public class BookedRoomController {
	
	@Autowired
	BookedRoomService bookedRoomService;
	
	@Autowired
	RoomService roomService;

	@GetMapping("all-bookings")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<List<BookingResponse>> getAllBookings(){
		List<BookedRoom> bookings = bookedRoomService.getAllBookings();
		List<BookingResponse> response = bookings.stream().map((booking) -> {
			BookingResponse bookingResponse = getBookingResponse(booking);
			return bookingResponse;
		}).collect(Collectors.toList());
		return new ResponseEntity<List<BookingResponse>>(response , HttpStatus.OK);
	}
	
	@GetMapping("confirmation/{confirmationCode}")
	public ResponseEntity<?> getBookingByConfirmationCode(@PathVariable String confirmationCode){
		try {
			
			BookedRoom booking = bookedRoomService.getBookingByConfirmationCode(confirmationCode);
			BookingResponse bookingResponse = getBookingResponse(booking);
			return new ResponseEntity<BookingResponse>(bookingResponse , HttpStatus.OK);
			
		} catch (ResourceNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
	}
	

	@PostMapping("/room/{roomId}/booking")
	public ResponseEntity<?> saveBooking(@PathVariable long roomId , @RequestBody BookedRoom requestBody){
		try {
			String confirmationCode = bookedRoomService.saveBooking(roomId , requestBody);
			return ResponseEntity.ok("Room booked successfully, Your booking confirmation code is : "+confirmationCode);
		} catch (InvalidBookingRequestException ex) {
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
	}
	
	@DeleteMapping("/booking/{bookingId}/{username}/delete")
	@PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_USER') and #email == principal.username)")
	public void cancelBooking(@PathVariable long bookingId , @PathVariable("username") String email){
		bookedRoomService.cancelBooking(bookingId);
	}
	
	private BookingResponse getBookingResponse(BookedRoom booking) {
		Room room = roomService.getRoomById(booking.getRoom().getId());
		RoomResponse roomResponse = new RoomResponse(room.getId() , room.getRoomType() , room.getRoomPrice());
		return new BookingResponse(booking.getBookingId() , booking.getCheckInDate() ,
								   booking.getCheckOutDate() , booking.getGuestFullName() , 
								   booking.getGuestEmail() , booking.getNoOfAdults() , 
								   booking.getNoOfChildren() , booking.getTotalNoOfGuests() , 
								   booking.getBookingConfirmationCode() , roomResponse);
	}
	
	@GetMapping("/user/{email}/bookings")
	public ResponseEntity<List<BookingResponse>> getBookingsByUserEmail(@PathVariable String email){
		List<BookedRoom> bookings = bookedRoomService.getBookingsByUserEmail(email);
		List<BookingResponse> response = new ArrayList<>();
		for(BookedRoom booking : bookings) {
			BookingResponse bookingResponse = getBookingResponse(booking);
			response.add(bookingResponse);
		}
		return ResponseEntity.ok(response);
	}
	
}
