package com.test.Scheduler.service;

import com.test.Scheduler.model.Booking;
import com.test.Scheduler.model.Room;
import com.test.Scheduler.repository.BookingRepository;
import com.test.Scheduler.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.time.format.DateTimeFormatter;


@Service
public class BookingService {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    @Autowired
    public BookingService(BookingRepository bookingRepository, RoomRepository roomRepository) {
        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;
    }
    @Autowired

    // Получить все бронирования
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    // Получить бронирование по ID
    public Optional<Booking> getBookingById(Long id) {
        return bookingRepository.findById(id);
    }

    // Создать новое бронирование
    public Booking createBooking(Booking booking) {
        // Проверяем существование комнаты
        Long roomId = booking.getRoom().getId();
        Optional<Room> room = roomRepository.findById(roomId);

        if (room.isEmpty()) {
            throw new IllegalArgumentException("Комната с ID " + roomId + " не существует");
        }

        booking.setRoom(room.get());


        return bookingRepository.save(booking);
    }
    public List<Booking> getFilteredBookings(String filterRoomID, String filterStartDate) {
        // Получаем все бронирования
        List<Booking> allBookings = getAllBookings();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Применяем фильтрацию
        return allBookings.stream()
                .filter(booking -> {
                    // Форматируем startDate
                    String formattedStartDate = booking.getStartTime().format(formatter);

                    // Проверяем filterRoomID (сравнение по id)
                    boolean roomIdMatches = filterRoomID.isEmpty() || String.valueOf(booking.getRoom().getId()).equals(filterRoomID);

                    // Проверяем filterStartDate (сравнение по дате)
                    boolean startDateMatches = filterStartDate.isEmpty() || formattedStartDate.startsWith(filterStartDate);

                    // Возвращаем результат фильтрации по обоим параметрам
                    return roomIdMatches && startDateMatches;
                })
                .toList();
    }
    // Удалить бронирование
    public boolean deleteBooking(Long id) {
        if (bookingRepository.existsById(id)) {
            bookingRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
