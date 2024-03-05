package com.roomreserve.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.roomreserve.entities.BookedRoom;

@Repository
public interface BookedRoomRepository extends JpaRepository<BookedRoom, Long>{

	Optional<BookedRoom> findByBookingConfirmationCode(String confirmationCode);

	List<BookedRoom> findByRoomId(Long id);

	List<BookedRoom> findByGuestEmail(String email);

}
