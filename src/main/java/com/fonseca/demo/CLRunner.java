package com.fonseca.demo;

import org.springframework.stereotype.Component;

import com.fonseca.demo.data.entity.Guest;
import com.fonseca.demo.data.entity.Reservation;
import com.fonseca.demo.data.entity.Room;
import com.fonseca.demo.data.repository.GuestRepository;
import com.fonseca.demo.data.repository.ReservationRepository;
import com.fonseca.demo.data.repository.RoomRepository;
import com.fonseca.demo.data.service.RoomReservationService;
import com.fonseca.demo.data.service.model.RoomReservation;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.CommandLineRunner;

@Component
public class CLRunner implements CommandLineRunner {

    private final RoomRepository roomRepository;
    private final GuestRepository guestRepository;
    private final ReservationRepository reservationRepository;
    private final RoomReservationService roomReservationService;

    public CLRunner(RoomRepository roomRepository, GuestRepository guestRepository,
            ReservationRepository reservationRepository, RoomReservationService roomReservationService) {
        this.roomRepository = roomRepository;
        this.guestRepository = guestRepository;
        this.reservationRepository = reservationRepository;
        this.roomReservationService = roomReservationService;
    }

    @Override
    public void run(String... args) throws Exception {

        System.out.println("=== ROOMS ===");
        List<Room> rooms = this.roomRepository.findAll();
        rooms.forEach(System.out::println);

        System.out.println("=== GUESTS ===");
        List<Guest> guests = this.guestRepository.findAll();
        guests.forEach(System.out::println);

        System.out.println("=== RESERVATIONS ===");
        List<Reservation> reservations = this.reservationRepository.findAll();
        reservations.forEach(System.out::println);

        System.out.println("=== RESERVATIONS AT ===");
        List<RoomReservation> roomReservations = this.roomReservationService.getRoomReservationsForDate("2023-08-28");
        roomReservations.forEach(System.out::println);

        // Optional<Room> room = this.roomRepository.findByRoomNumberIgnoreCase("p1");
        // System.out.println(room);
    }

}
