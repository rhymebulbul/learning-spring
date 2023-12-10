package com.bulbul.rhyme.learningspring.business;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.bulbul.rhyme.learningspring.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    private final RoomRepository roomRepository;
    private final GuestRepository guestRepository;
    private final ReservationRepository reservationRepository;

    public ReservationService(RoomRepository roomRepository, GuestRepository guestRepository, ReservationRepository reservationRepository) {
        this.roomRepository = roomRepository;
        this.guestRepository = guestRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<RoomReservation> getRoomReservationsForDate(Date date) {
        Iterable<Room> rooms = this.roomRepository.findAll();
        Map<Long, RoomReservation> roomReservationMap = new HashMap();
        rooms.forEach(room -> {
            RoomReservation roomReservation = new RoomReservation();
            roomReservation.setRoomId(room.getId());
            roomReservation.setRoomName(room.getName());
            roomReservation.setRoomNumber(room.getRoomNumber());
            roomReservationMap.put(room.getId(), roomReservation);
        });
        Iterable<Reservation> reservations = this.reservationRepository.findReservationByReservationDate(new java.sql.Date(date.getTime()));
        reservations.forEach(reservation -> {
            RoomReservation roomReservation = roomReservationMap.get(reservation.getRoomId());
            roomReservation.setDate(date);
            Guest guest = this.guestRepository.findById(reservation.getGuestId()).get();
            roomReservation.setFirstName(guest.getFirstName());
            roomReservation.setLastName(guest.getLastName());
            roomReservation.setGuestId(guest.getGuestId());
        });
        List<RoomReservation> roomReservations = new ArrayList<>();
        for (Long id : roomReservationMap.keySet()) {
            roomReservations.add(roomReservationMap.get(id));
        }
        roomReservations.sort(new Comparator<RoomReservation>() {
            @Override
            public int compare(RoomReservation o1, RoomReservation o2) {
                if (o1.getRoomName().equals(o2.getRoomName())) {
                    return o1.getRoomNumber().compareTo(o2.getRoomNumber());
                }
                return o1.getRoomName().compareTo(o2.getRoomName());
            }
        });
        return roomReservations;
    }

    public List<Guest> getHotelGuests(){
        Iterable<Guest> guests = this.guestRepository.findAll();
        List<Guest> guestList = new ArrayList<>();
        guests.forEach(guest->{guestList.add(guest);});
        guestList.sort(new Comparator<Guest>() {
            @Override
            public int compare(Guest guest, Guest t1) {
                if(guest.getLastName().equals(t1.getLastName())){
                    return guest.getFirstName().compareTo(t1.getFirstName());
                }
                return guest.getLastName().compareTo(t1.getLastName());
            }
        });
        return guestList;
    }

    public void addGuest(Guest guest) {
        if (null == guest){
            throw new RuntimeException("Guest cannot be null");
        }
        this.guestRepository.save(guest);
    }

    public List<Room> getRooms() {
        Iterable<Room> rooms = this.roomRepository.findAll();
        List<Room> roomList = new ArrayList<>();
        rooms.forEach(room -> {roomList.add(room);});
        roomList.sort(new Comparator<Room>() {
            @Override
            public int compare(Room room, Room t1) {
                return room.getRoomNumber().compareTo(t1.getRoomNumber());
            }
        });
        return roomList;
    }
}

