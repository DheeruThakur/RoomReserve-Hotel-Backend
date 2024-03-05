package com.roomreserve.response;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.util.codec.binary.Base64;


public class RoomResponse {

	private Long id;
	private String roomType;
	private BigDecimal roomPrice;
	private boolean isBooked = false;
	private String photo;
	private List<BookingResponse> bookings = new ArrayList<>();
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
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public List<BookingResponse> getBookings() {
		return bookings;
	}
	public void setBookings(List<BookingResponse> bookings) {
		this.bookings = bookings;
	}
	public RoomResponse(Long id, String roomType, BigDecimal roomPrice) {
		super();
		this.id = id;
		this.roomType = roomType;
		this.roomPrice = roomPrice;
	}
	public RoomResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	public RoomResponse(Long id, String roomType, BigDecimal roomPrice, boolean isBooked,
			byte[] photoBytes , List<BookingResponse> bookings) {
		super();
		this.id = id;
		this.roomType = roomType;
		this.roomPrice = roomPrice;
		this.isBooked = isBooked;
		this.photo = photoBytes != null ? Base64.encodeBase64String(photoBytes) : null;
		this.bookings = bookings;
	}
	
	
}
