package com.roomreserve.response;

import java.time.LocalDate;

public class BookingResponse {

	private long bookingId;
	private LocalDate checkInDate;
	private LocalDate checkOutDate;
	private String guestFullName;	
	private String guestEmail;
	private int noOfAdults;
	private int noOfChildren;
	private int totalNoOfGuests;
	private String bookingConfirmationCode;
	private RoomResponse room;
	
	
	public long getBookingId() {
		return bookingId;
	}
	public void setBookingId(long bookingId) {
		this.bookingId = bookingId;
	}
	public LocalDate getCheckInDate() {
		return checkInDate;
	}
	public void setCheckInDate(LocalDate checkInDate) {
		this.checkInDate = checkInDate;
	}
	public LocalDate getCheckOutDate() {
		return checkOutDate;
	}
	public void setCheckOutDate(LocalDate checkOutDate) {
		this.checkOutDate = checkOutDate;
	}
	public String getGuestFullName() {
		return guestFullName;
	}
	public void setGuestFullName(String guestFullName) {
		this.guestFullName = guestFullName;
	}
	public String getGuestEmail() {
		return guestEmail;
	}
	public void setGuestEmail(String guestEmail) {
		this.guestEmail = guestEmail;
	}
	public int getNoOfAdults() {
		return noOfAdults;
	}
	public void setNoOfAdults(int noOfAdults) {
		this.noOfAdults = noOfAdults;
	}
	public int getNoOfChildren() {
		return noOfChildren;
	}
	public void setNoOfChildren(int noOfChildren) {
		this.noOfChildren = noOfChildren;
	}
	public int getTotalNoOfGuests() {
		return totalNoOfGuests;
	}
	public void setTotalNoOfGuests(int totalNoOfGuests) {
		this.totalNoOfGuests = totalNoOfGuests;
	}
	public String getBookingConfirmationCode() {
		return bookingConfirmationCode;
	}
	public void setBookingConfirmationCode(String bookingConfirmationCode) {
		this.bookingConfirmationCode = bookingConfirmationCode;
	}
	public RoomResponse getRoom() {
		return room;
	}
	public void setRoom(RoomResponse room) {
		this.room = room;
	}
	public BookingResponse(long bookingId, LocalDate checkInDate, LocalDate checkOutDate,
			String bookingConfirmationCode) {
		super();
		this.bookingId = bookingId;
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
		this.bookingConfirmationCode = bookingConfirmationCode;
	}
	public BookingResponse(long bookingId, LocalDate checkInDate, LocalDate checkOutDate, String guestFullName,
			String guestEmail, int noOfAdults, int noOfChildren, int totalNoOfGuests, String bookingConfirmationCode,
			RoomResponse room) {
		super();
		this.bookingId = bookingId;
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
		this.guestFullName = guestFullName;
		this.guestEmail = guestEmail;
		this.noOfAdults = noOfAdults;
		this.noOfChildren = noOfChildren;
		this.totalNoOfGuests = totalNoOfGuests;
		this.bookingConfirmationCode = bookingConfirmationCode;
		this.room = room;
	}
	public BookingResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
