package com.roomreserve.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class BookedRoom {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bookingId;
	
	@Column(name = "check_In")
	private LocalDate checkInDate;
	
	@Column(name = "check_Out")
	private LocalDate checkOutDate;
	
	@Column(name = "guest_FullName")
	private String guestFullName;
	
	@Column(name = "guest_Email")
	private String guestEmail;
	
	@Column(name = "adults")
	private int noOfAdults;
	
	@Column(name = "children")
	private int noOfChildren;
	
	@Column(name = "total_guest")
	private int totalNoOfGuests;
	
	@Column(name = "confirmation_Code")
	private String bookingConfirmationCode;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "room_Id")
	private Room room;
	
	
	public void calculateTotalNoOfGuests() {
		this.totalNoOfGuests = this.noOfAdults + this.noOfChildren;
	}

	public Long getBookingId() {
		return bookingId;
	}

	public void setBookingId(Long bookingId) {
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
		calculateTotalNoOfGuests();
	}

	public int getNoOfChildren() {
		return noOfChildren;
	}

	public void setNoOfChildren(int noOfChildren) {
		this.noOfChildren = noOfChildren;
		calculateTotalNoOfGuests();
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

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public BookedRoom(Long bookingId, LocalDate checkInDate, LocalDate checkOutDate, String guestFullName,
			String guestEmail, int noOfAdults, int noOfChildren, int totalNoOfGuests, String bookingConfirmationCode,
			Room room) {
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

	public BookedRoom() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
