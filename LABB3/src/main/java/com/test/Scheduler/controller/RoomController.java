package com.test.Scheduler.controller;

import com.test.Scheduler.model.Room;
import com.test.Scheduler.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    // Получить все комнаты в формате XML с XSLT
    @GetMapping(value = "/xml", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> getAllRoomsAsXml() {
        List<Room> rooms = roomService.getAllRooms();

        // Генерация XML вручную с добавлением ссылки на XSL
        StringBuilder xmlBuilder = new StringBuilder();
        xmlBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        xmlBuilder.append("<?xml-stylesheet type=\"text/xsl\" href=\"/room.xsl\"?>\n");
        xmlBuilder.append("<rooms>\n");

        for (Room room : rooms) {
            xmlBuilder.append("  <room>\n");
            xmlBuilder.append("    <id>").append(room.getId()).append("</id>\n");
            xmlBuilder.append("    <name>").append(room.getName()).append("</name>\n");
            xmlBuilder.append("    <capacity>").append(room.getCapacity()).append("</capacity>\n");
            xmlBuilder.append("  </room>\n");
        }

        xmlBuilder.append("</rooms>\n");

        return ResponseEntity.ok(xmlBuilder.toString());
    }


    @GetMapping(value = "/{id}/xml", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> getRoomByIdAsXml(@PathVariable Long id) {
        return roomService.getRoomById(id)
                .map(room -> {
                    StringBuilder xmlBuilder = new StringBuilder();
                    xmlBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
                    xmlBuilder.append("<?xml-stylesheet type=\"text/xsl\" href=\"/xsl/room.xsl\"?>\n");
                    xmlBuilder.append("<room>\n");
                    xmlBuilder.append("  <id>").append(room.getId()).append("</id>\n");
                    xmlBuilder.append("  <name>").append(room.getName()).append("</name>\n");
                    xmlBuilder.append("  <capacity>").append(room.getCapacity()).append("</capacity>\n");
                    xmlBuilder.append("</room>\n");

                    return ResponseEntity.ok(xmlBuilder.toString());
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @GetMapping
    public List<Room> getAllRooms() {
        return roomService.getAllRooms();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> getRoomById(@PathVariable Long id) {
        return roomService.getRoomById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Room> createRoom(@RequestBody Room room) {
        Room savedRoom = roomService.createRoom(room);
        return new ResponseEntity<>(savedRoom, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Room> updateRoom(@PathVariable Long id, @RequestBody Room room) {
        return roomService.updateRoom(id, room)
                .map(updatedRoom -> ResponseEntity.ok(updatedRoom))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        if (roomService.deleteRoom(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
