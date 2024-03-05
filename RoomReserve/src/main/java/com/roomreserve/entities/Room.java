package com.roomreserve.entities;

import java.math.BigDecimal;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;

@Entity
public class Room {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String roomType;
	private BigDecimal roomPrice;
	private boolean isBooked = false;
	
	@Lob
	private Blob photo;

	@OneToMany(mappedBy = "room" , fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<BookedRoom> bookings = new ArrayList<>();

	public void addBooking(BookedRoom booking) {
		bookings.add(booking);
		booking.setRoom(this);
		String confirmationCode = RandomStringUtils.randomNumeric(10);
		booking.setBookingConfirmationCode(confirmationCode);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public BigDecimal getRoomPrice() {
		return roomPrice;
	}

	public void setRoomPrice(BigDecimal roomPrice) {
		this.roomPrice = roomPrice;
	}

	public boolean isBooked() {
		return isBooked;
	}

	public void setBooked(boolean isBooked) {
		this.isBooked = isBooked;
	}

	public Blob getPhoto() {
		return photo;
	}

	public void setPhoto(Blob photo) {
		this.photo = photo;
	}

	public List<BookedRoom> getBookings() {
		return bookings;
	}

	public void setBookings(List<BookedRoom> bookings) {
		this.bookings = bookings;
	}

	public Room(Long id, String roomType, BigDecimal roomPrice, boolean isBooked, Blob photo,
			List<BookedRoom> bookings) {
		super();
		this.id = id;
		this.roomType = roomType;
		this.roomPrice = roomPrice;
		this.isBooked = isBooked;
		this.photo = photo;
		this.bookings = bookings;
	}

	public Room() {
		super();
	}
}
