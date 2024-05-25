package com.fonseca.demo.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fonseca.demo.data.entity.Guest;

public interface GuestRepository extends JpaRepository<Guest, Long> {

}
