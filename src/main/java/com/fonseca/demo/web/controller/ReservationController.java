package com.fonseca.demo.web.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fonseca.demo.data.service.RoomReservationService;

import io.micrometer.common.util.StringUtils;

@Controller
@RequestMapping("/reservations")
public class ReservationController {

    private final RoomReservationService roomReservationService;

    public ReservationController(RoomReservationService roomReservationService) {
        this.roomReservationService = roomReservationService;
    }

    @GetMapping
    public String getReservations(@RequestParam(value = "date", required = false) String date, Model model) {
        // Step 1: Get the current date using LocalDate
        LocalDate localDate = LocalDate.now();

        // Step 2: Convert LocalDate to java.sql.Date
        Date sqlDate = Date.valueOf(localDate);

        // Step 3: Format the java.sql.Date to a String
        // Define the desired date format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Convert java.sql.Date to LocalDate for formatting
        LocalDate dateToFormat = sqlDate.toLocalDate();

        // Format the date as a string
        String formattedDate = dateToFormat.format(formatter);

        // Output the formatted date
        System.out.println("Today's date in string format: " + formattedDate);

        if (StringUtils.isNotBlank(date)) {
            model.addAttribute("reservations", this.roomReservationService.getRoomReservationsForDate(date));
        } else {
            model.addAttribute("reservations",
                    this.roomReservationService.getRoomReservationsForDate(formattedDate));
        }
        return "reservation-list";
    }

}
