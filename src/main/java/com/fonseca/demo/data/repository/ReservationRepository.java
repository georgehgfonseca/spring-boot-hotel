package com.fonseca.demo.data.repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.fonseca.demo.data.entity.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Optional<Reservation> findById(int reservationId);

    List<Reservation> findAllByResDate(Date resDate);

}
