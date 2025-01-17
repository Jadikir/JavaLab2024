package com.test.Scheduler.service;

import com.test.Scheduler.model.Room;
import com.test.Scheduler.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;


    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }


    public Optional<Room> getRoomById(Long id) {
        return roomRepository.findById(id);
    }


    public Room createRoom(Room room) {
        return roomRepository.save(room);
    }


    public Optional<Room> updateRoom(Long id, Room updatedRoom) {
        return roomRepository.findById(id).map(room -> {
            room.setName(updatedRoom.getName());
            room.setCapacity(updatedRoom.getCapacity());
            return Optional.of(roomRepository.save(room));  
        }).orElse(Optional.empty());
    }


    public boolean deleteRoom(Long id) {
        if (roomRepository.existsById(id)) {
            roomRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
