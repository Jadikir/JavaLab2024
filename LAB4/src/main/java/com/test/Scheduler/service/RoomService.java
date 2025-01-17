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

    @Autowired
    private ChangeLogService changeLogService;


    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }


    public Optional<Room> getRoomById(Long id) {
        return roomRepository.findById(id);
    }


    public Room createRoom(Room room) {
        Room savedRoom = roomRepository.save(room);

        // Логирование создания комнаты
        changeLogService.logChange(
                "INSERT",
                savedRoom.getId(),
                Room.class.getSimpleName(),
                String.format("Created room with name '%s' and capacity '%d'", savedRoom.getName(), savedRoom.getCapacity())
        );

        return savedRoom;
    }


    public Optional<Room> updateRoom(Long id, Room updatedRoom) {
        return roomRepository.findById(id).map(room -> {
            String oldDetails = String.format("Name: '%s', Capacity: '%d'", room.getName(), room.getCapacity());


            room.setName(updatedRoom.getName());
            room.setCapacity(updatedRoom.getCapacity());
            Room savedRoom = roomRepository.save(room);


            changeLogService.logChange(
                    "UPDATE",
                    savedRoom.getId(),
                    Room.class.getSimpleName(),
                    String.format("Updated room. Old details: [%s], New details: [Name: '%s', Capacity: '%d']",
                            oldDetails, savedRoom.getName(), savedRoom.getCapacity())
            );

            return Optional.of(savedRoom);
        }).orElse(Optional.empty());
    }


    public boolean deleteRoom(Long id) {
        if (roomRepository.existsById(id)) {
            roomRepository.deleteById(id);

            changeLogService.logChange(
                    "DELETE",
                    id,
                    Room.class.getSimpleName(),
                    String.format("Deleted room with ID '%d'", id)
            );

            return true;
        }
        return false;
    }
}
