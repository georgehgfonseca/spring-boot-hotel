package com.fonseca.demo.web.api;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fonseca.demo.data.entity.Reservation;
import com.fonseca.demo.data.repository.ReservationRepository;
import com.fonseca.demo.data.service.RoomReservationService;
import com.fonseca.demo.data.service.model.RoomReservation;
import com.fonseca.demo.web.exception.BadRequestException;
import com.fonseca.demo.web.exception.NotFoundException;

import io.micrometer.common.util.StringUtils;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/reservations")
public class ReservationApiController {
    private final ReservationRepository reservationRepository;
    private final RoomReservationService roomReservationService;

    public ReservationApiController(ReservationRepository reservationRepository,
            RoomReservationService roomReservationService) {
        this.reservationRepository = reservationRepository;
        this.roomReservationService = roomReservationService;
    }

    @GetMapping
    public List<Reservation> getAllReservations(@RequestParam(value = "date", required = false) String dateString) {
        if (StringUtils.isNotBlank(dateString)) {
            return this.reservationRepository.findAllByResDate(Date.valueOf(dateString));
        }
        return this.reservationRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Reservation createReservation(@RequestBody Reservation reservation) {
        return this.reservationRepository.save(reservation);
    }

    @GetMapping("/{id}")
    public Reservation getReservation(@PathVariable("id") long id) {
        Optional<Reservation> reservation = this.reservationRepository.findById(id);
        if (reservation.isEmpty()) {
            throw new NotFoundException("reservation not found with id: " + id);
        }
        return reservation.get();
    }

    @GetMapping("/forDate/{date}")
    public List<RoomReservation> getReservationByDate(@PathVariable("date") String date) {
        List<RoomReservation> roomReservations = this.roomReservationService.getRoomReservationsForDate(date);
        return roomReservations;
    }

    @PutMapping("/{id}")
    public Reservation updateReservation(@PathVariable("id") long id, @RequestBody Reservation reservation) {
        if (id != reservation.getId()) {
            throw new BadRequestException("id on path doesn't match the body");
        }
        return this.reservationRepository.save(reservation);
    }

    @DeleteMapping("/id")
    @ResponseStatus(HttpStatus.RESET_CONTENT)
    public void deleteReservation(@PathVariable("id") long id) {
        this.reservationRepository.deleteById(id);
    }

}
