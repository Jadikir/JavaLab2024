package com.test.Scheduler.controller;

import com.test.Scheduler.model.Booking;
import com.test.Scheduler.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    // Получить все бронирования в формате XML с XSLT
    @GetMapping(value = "/xml", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> getFilteredBookingsAsXml(
            @RequestParam(value = "filterRoomID", required = false, defaultValue = "") String filterRoomID,
            @RequestParam(value = "filterStartDate", required = false, defaultValue = "") String filterStartDate) {

        // Получение фильтрованных данных через сервис
        List<Booking> filteredBookings = bookingService.getFilteredBookings(filterRoomID, filterStartDate);

        // Генерация XML вручную с добавлением ссылки на XSL
        StringBuilder xmlBuilder = new StringBuilder();
        xmlBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        xmlBuilder.append("<?xml-stylesheet type=\"text/xsl\" href=\"/booking.xsl\"?>\n");
        xmlBuilder.append("<bookings>\n");

        for (Booking booking : filteredBookings) {
            xmlBuilder.append("  <booking>\n");
            xmlBuilder.append("    <id>").append(booking.getId()).append("</id>\n");
            xmlBuilder.append("    <room>").append(booking.getRoom().getName()).append("</room>\n");
            xmlBuilder.append("    <roomID>").append(booking.getRoom().getId()).append("</roomID>\n");
            xmlBuilder.append("    <startDate>").append(booking.getStartTime()).append("</startDate>\n");
            xmlBuilder.append("    <endDate>").append(booking.getEndTime()).append("</endDate>\n");
            xmlBuilder.append("  </booking>\n");
        }

        xmlBuilder.append("</bookings>\n");

        return ResponseEntity.ok(xmlBuilder.toString());
    }


    @GetMapping
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long id) {
        return bookingService.getBookingById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) {
        Booking savedBooking = bookingService.createBooking(booking);
        return new ResponseEntity<>(savedBooking, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
        if (bookingService.deleteBooking(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
