package com.bulbul.rhyme.learningspring.util;
import java.util.Date;
import java.util.List;

import com.bulbul.rhyme.learningspring.business.ReservationService;
import com.bulbul.rhyme.learningspring.business.RoomReservation;
import com.bulbul.rhyme.learningspring.data.*;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class AppStartupEvent implements ApplicationListener<ApplicationReadyEvent> {
//    private final RoomRepository roomRepository;
//    private final GuestRepository guestRepository;
//    private final ReservationRepository reservationRepository;
    private final ReservationService reservationService;
    private final DateUtils dateUtils;

//    public AppStartupEvent(RoomRepository roomRepository, GuestRepository guestRepository, ReservationRepository reservationRepository) {
//        this.roomRepository = roomRepository;
//        this.guestRepository = guestRepository;
//        this.reservationRepository = reservationRepository;
//    }

    public AppStartupEvent(ReservationService reservationService, DateUtils dateUtils) {
        this.reservationService = reservationService;
        this.dateUtils = dateUtils;
    }
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
//        Iterable<Room> rooms = this.roomRepository.findAll();
//        rooms.forEach(System.out::println);
//
//        Iterable<Guest> guests = this.guestRepository.findAll();
//        guests.forEach(System.out::println);
//
//        Iterable<Reservation> reservations = this.reservationRepository.findAll();
//        reservations.forEach(System.out::println);

        Date date = this.dateUtils.createDateFromDateString("2022-01-01");
        List<RoomReservation> reservations = this.reservationService.getRoomReservationsForDate(date);
        reservations.forEach(System.out::println);
    }
//    @Override
//    public boolean supportsAsyncExecution() {
//        return ApplicationListener.super.supportsAsyncExecution();
//    }
}
