package com.roomreserve.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.sql.rowset.serial.SerialException;

import org.springframework.web.multipart.MultipartFile;

import com.roomreserve.entities.Room;
import com.roomreserve.response.RoomResponse;

public interface RoomService {

	Room addNewRoom(MultipartFile file, String roomType, BigDecimal roomPrice) throws SQLException, IOException;

	List<String> getAllRoomTypes();

	List<Room> getAllRoom();

	byte[] getRoomPhotoByRoomId(Long id) throws SQLException;

	void deleteRoomById(long roomId);

	Room updateRoom(long roomId, String roomType, BigDecimal roomPrice, byte[] photoBytes);

	Room getRoomById(long roomId);

	List<Room> getAvailableRooms(LocalDate checkInDate, LocalDate checkOutDate, String roomType);
}
