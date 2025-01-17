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



    // Получить все комнаты
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    // Получить комнату по ID
    public Optional<Room> getRoomById(Long id) {
        return roomRepository.findById(id);
    }

    // Создать новую комнату
    public Room createRoom(Room room) {
        Room savedRoom = roomRepository.save(room);

        // Логирование создания комнаты


        return savedRoom;
    }

    // Обновить данные комнаты
    public Room updateRoom(Long id, Room room) {
        Room existingRoom = roomRepository.findById(id).orElse(null);
        if (existingRoom != null) {
            existingRoom.setName(room.getName());
            existingRoom.setCapacity(room.getCapacity());
            return roomRepository.save(existingRoom);  // Сохраняем обновленные данные
        }
        return null;  // Если комната не найдена, возвращаем null
    }

    // Удалить комнату
    public boolean deleteRoom(Long id) {
        if (roomRepository.existsById(id)) {
            roomRepository.deleteById(id);

            return true;
        }
        return false;
    }
}
