package com.roomreserve.serviceImpl;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import javax.sql.rowset.serial.SerialBlob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.multipart.MultipartFile;

import com.roomreserve.entities.Room;
import com.roomreserve.exception.InternalServerException;
import com.roomreserve.exception.ResourceNotFoundException;
import com.roomreserve.repository.RoomRepository;
import com.roomreserve.service.RoomService;


@Service
public class RoomServiceImpl implements RoomService{

	
	@Autowired
	RoomRepository roomRepository;
	
	@Override
	public Room addNewRoom(MultipartFile file, String roomType, BigDecimal roomPrice) throws SQLException, IOException {
        Room room = new Room();
        room.setRoomType(roomType);
        room.setRoomPrice(roomPrice);
        if (!file.isEmpty()){
            byte[] photoBytes = file.getBytes();
            Blob photoBlob = new SerialBlob(photoBytes);
            room.setPhoto(photoBlob);
        }
        return roomRepository.save(room);
    }

	@Override
	public List<String> getAllRoomTypes() {
		return roomRepository.findDistinctRoomTypes();
	}

	@Override
	public List<Room> getAllRoom() {
		return roomRepository.findAll();
	}

	@Override
	public byte[] getRoomPhotoByRoomId(Long id) throws SQLException {
		Optional<Room> room = roomRepository.findById(id);
		if(room.isEmpty()) {
			throw new ResourceNotFoundException("Room not found in DataBase");
		}
		Blob photoBlob = room.get().getPhoto();
		if(photoBlob != null) {
			return photoBlob.getBytes(1, (int) photoBlob.length());
		}
		return null;
	}

	@Override
	public void deleteRoomById(long roomId) {
		Optional<Room> theRoom = roomRepository.findById(roomId);
		if(theRoom.isPresent()) {
			roomRepository.deleteById(roomId);
		}else {
			throw new ResourceNotFoundException("Room not found with this id");
		}
	}

	@Override
	public Room updateRoom(long roomId, String roomType, BigDecimal roomPrice, byte[] photoBytes) {
		Room room = roomRepository.findById(roomId).orElseThrow(() -> new ResourceNotFoundException("Room not found"));
		if (roomType != null) room.setRoomType(roomType);
		if (roomPrice != null) room.setRoomPrice(roomPrice);
		if(photoBytes != null && photoBytes.length > 0) {
			try {
				room.setPhoto(new SerialBlob(photoBytes));
			} catch (SQLException ex) {
				throw new InternalServerException("Error updating room");
			}
		}
		return roomRepository.save(room);
	}

	@Override
	public Room getRoomById(long roomId) {
		Room room = roomRepository.findById(roomId).orElseThrow(() -> new ResourceNotFoundException("Room not found"));
		return room;
	}

	@Override
	public List<Room> getAvailableRooms(LocalDate checkInDate, LocalDate checkOutDate, String roomType) {
		return roomRepository.findAvailableRoomsByDateAndType(checkInDate , checkOutDate , roomType);
	}

}
