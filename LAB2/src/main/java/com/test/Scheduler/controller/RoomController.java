package com.test.Scheduler.controller;

import com.test.Scheduler.model.Room;
import com.test.Scheduler.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@Controller
@RequestMapping("/rooms")
public class RoomController {

    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    // Получить все комнаты
    @GetMapping
    public String getAllRooms(Model model) {
        List<Room> rooms = roomService.getAllRooms();
        model.addAttribute("rooms", rooms);
        return "rooms";  // Шаблон для отображения списка комнат
    }

    // Получить комнату по ID
    @GetMapping("/{id}")
    public String getRoomById(@PathVariable Long id, Model model) {
        Room room = roomService.getRoomById(id).orElse(null);
        if (room == null) {
            return "redirect:/rooms";  // Если комната не найдена, перенаправляем на список
        }
        model.addAttribute("room", room);
        return "roomDetail";  // Шаблон для отображения информации о комнате
    }

    // Создать комнату
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("room", new Room());  // Пустая комната для формы
        return "createRoom";  // Шаблон для создания комнаты
    }

    @PostMapping("/create")
    public String createRoom(@ModelAttribute Room room) {
        roomService.createRoom(room);
        return "redirect:/rooms";  // После создания редирект на список комнат
    }

    // Обновить информацию о комнате
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        Room room = roomService.getRoomById(id).orElse(null);
        if (room == null) {
            return "redirect:/rooms";  // Если комната не найдена, перенаправляем на список
        }
        model.addAttribute("room", room);  // Передаем комнату в модель
        return "updateRoom";  // Шаблон для обновления комнаты
    }

    // Метод для обновления комнаты
    @PostMapping("/update/{id}")
    public String updateRoom(@PathVariable Long id, @ModelAttribute Room room) {
        room.setId(id);  // Устанавливаем ID для обновления конкретной комнаты
        Room updatedRoom = roomService.updateRoom(id, room);
        if (updatedRoom == null) {
            return "redirect:/rooms";  // Если не удалось обновить, перенаправляем на список
        }
        return "redirect:/rooms";  // После обновления перенаправляем на список комнат
    }

    // Удалить комнату
    @GetMapping("/delete/{id}")
    public String deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return "redirect:/rooms";  // После удаления редирект на список комнат
    }
}

