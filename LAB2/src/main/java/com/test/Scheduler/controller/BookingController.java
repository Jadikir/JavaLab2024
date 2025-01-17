package com.test.Scheduler.controller;

import com.test.Scheduler.model.Booking;
import com.test.Scheduler.service.BookingService;
import com.test.Scheduler.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;
    private final RoomService roomService;  // Сервис для работы с комнатами

    @Autowired
    public BookingController(BookingService bookingService, RoomService roomService) {
        this.bookingService = bookingService;
        this.roomService = roomService;
    }

    // Получить все бронирования
    @GetMapping
    public String getAllBookings(Model model) {
        List<Booking> bookings = bookingService.getAllBookings();
        model.addAttribute("bookings", bookings);
        return "bookings"; // Отправим данные в шаблон "bookings.html"
    }

    // Удалить бронирование
    @GetMapping("/delete/{id}")
    public String deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
        return "redirect:/bookings"; // После удаления редирект на список бронирований
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("booking", new Booking());  // Передаем пустую модель для формы
        model.addAttribute("rooms", roomService.getAllRooms());  // Получаем все комнаты и передаем в модель
        return "createBooking";  // Шаблон для формы создания
    }

    // Обработка данных формы для создания нового бронирования
    @PostMapping("/create")
    public String createBooking(@ModelAttribute Booking booking, Model model) {
        try {
            // Создание нового бронирования
            bookingService.createBooking(booking);
            model.addAttribute("message", "Бронирование успешно создано!");
            return "redirect:/bookings";  // Перенаправление на страницу со списком бронирований
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());  // Если ошибка, выводим ее в форме
            return "createBooking";  // Перезагружаем форму
        }
    }
}