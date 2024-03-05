package com.roomreserve.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;
import javax.sql.rowset.serial.SerialBlob;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.roomreserve.entities.BookedRoom;
import com.roomreserve.entities.Room;
import com.roomreserve.exception.PhotoRetrievalException;
import com.roomreserve.response.BookingResponse;
import com.roomreserve.response.RoomResponse;
import com.roomreserve.service.BookedRoomService;
import com.roomreserve.service.RoomService;

@RestController
@RequestMapping("/rooms")
public class RoomController {
	
	@Autowired
	private RoomService roomService;
	
	@Autowired
	private BookedRoomService bookedRoomService;
	
	@PostMapping("/add/new-room")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<RoomResponse> addNewRoom(
            @RequestParam("photo") MultipartFile photo,
            @RequestParam("roomType") String roomType,
            @RequestParam("roomPrice") BigDecimal roomPrice) throws SQLException, IOException {
        Room savedRoom = roomService.addNewRoom(photo, roomType, roomPrice);
        RoomResponse response = new RoomResponse(savedRoom.getId(), savedRoom.getRoomType(),
                savedRoom.getRoomPrice());
        return ResponseEntity.ok(response);
    }

	@GetMapping("/room-types")
	public List<String> getRoomTypes(){
		return roomService.getAllRoomTypes();
	}
	
	@GetMapping("/all-rooms")
	public ResponseEntity<List<RoomResponse>> getAllRooms() throws SQLException {
        List<Room> rooms = roomService.getAllRoom();
        List<RoomResponse> roomResponses = new ArrayList<>();
        for (Room room : rooms) {
            byte[] photoBytes = roomService.getRoomPhotoByRoomId(room.getId());
            if (photoBytes != null && photoBytes.length > 0) {
                String base64Photo = Base64.encodeBase64String(photoBytes);
                RoomResponse roomResponse = getRoomResponse(room);
                roomResponse.setPhoto(base64Photo);
                roomResponses.add(roomResponse);
            }
        }
        return ResponseEntity.ok(roomResponses);
    }
	
	@GetMapping("/available-rooms")
	public ResponseEntity<List<RoomResponse>> getAvailableRooms(@RequestParam("checkInDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate checkInDate ,
																@RequestParam("checkOutDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate checkOutDate , 
																@RequestParam String roomType) throws SQLException {
		
		List<Room> rooms = roomService.getAvailableRooms(checkInDate , checkOutDate , roomType);
		List<RoomResponse> responses = new ArrayList<>();
		for(Room room : rooms) {
			byte[] photoBytes = roomService.getRoomPhotoByRoomId(room.getId());
			if(photoBytes != null && photoBytes.length > 0) {
				String encodeBase64String = Base64.encodeBase64String(photoBytes);
				RoomResponse roomResponse = getRoomResponse(room);
				roomResponse.setPhoto(encodeBase64String);
				responses.add(roomResponse);
			}
		}
		if(responses.isEmpty()) {
			return ResponseEntity.noContent().build();
		}else {
			return ResponseEntity.ok(responses);
		}
	}
	
	
	@DeleteMapping("/delete/room/{roomId}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<HttpStatus> deleteRoom(@PathVariable long roomId){
		roomService.deleteRoomById(roomId);
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}
	
	@PutMapping("/update/{roomId}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<RoomResponse> updateRoom(@PathVariable long roomId , 
													@RequestParam(required = false) String roomType,
													@RequestParam(required = false) BigDecimal roomPrice ,
													@RequestParam(required = false) MultipartFile photo) throws IOException, SQLException{
		
		byte[] photoBytes = ( photo != null && !photo.isEmpty() ) ? 
				photo.getBytes() : 
				roomService.getRoomPhotoByRoomId(roomId);
		
		Blob photoBlob = photoBytes != null && photoBytes.length > 0 ? new SerialBlob(photoBytes) : null;
		Room theRoom = roomService.updateRoom(roomId , roomType , roomPrice , photoBytes);
		theRoom.setPhoto(photoBlob);
		RoomResponse roomResponse = getRoomResponse(theRoom);
		return new ResponseEntity<RoomResponse>(roomResponse , HttpStatus.OK);
	}
	
	@GetMapping("/{roomId}")
	public ResponseEntity<RoomResponse> getRoomById(@PathVariable long roomId){
		Room theRoom = roomService.getRoomById(roomId);
		RoomResponse roomResponse = getRoomResponse(theRoom);
		return new ResponseEntity<RoomResponse>(roomResponse , HttpStatus.OK);
	}
	
	
	private RoomResponse getRoomResponse(Room room) {
		List<BookedRoom> bookings = getAllBookingsByRoomId(room.getId());
		
		List<BookingResponse> bookingInfo = bookings.stream().map(booking -> 
										new BookingResponse(booking.getBookingId(), booking.getCheckInDate(),
											booking.getCheckOutDate(), booking.getBookingConfirmationCode())).toList();
		
		byte[] photoBytes = null;
		Blob blobPhoto = room.getPhoto();
		if(blobPhoto != null) {
			try {
				photoBytes = blobPhoto.getBytes(1, (int) blobPhoto.length());
			} catch (SQLException e) {
				throw new PhotoRetrievalException("Error retrieving photo");
			}
		}
		return new RoomResponse(room.getId() , room.getRoomType() , room.getRoomPrice() , room.isBooked() , photoBytes , bookingInfo) ;
	}

	private List<BookedRoom> getAllBookingsByRoomId(Long id) {
		List<BookedRoom> allBookingsByRoomId = bookedRoomService.getAllBookingsByRoomId(id);
		return allBookingsByRoomId;
	}
	
	

}
