package com.chatapp.controller;

import com.chatapp.entities.Message;
import com.chatapp.entities.Room;
import com.chatapp.payload.RoomRequest;
import com.chatapp.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rooms")
@CrossOrigin("http://localhost:5173")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @PostMapping
    public ResponseEntity<?> createRoom(@RequestBody RoomRequest roomRequest) {
        try {
            Room room = roomService.createRoom(roomRequest.getRoomId());
            return ResponseEntity.status(HttpStatus.CREATED).body(room);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<?> joinRoom(@PathVariable String roomId) {
        try {
            Room room = roomService.getRoomByRoomId(roomId);
            return ResponseEntity.ok(room);
        } catch (RuntimeException e) {
            if (e.getMessage().equals("Room not found!")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{roomId}/messages")
    public ResponseEntity<List<Message>> getMessages(
            @PathVariable String roomId,
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "size", defaultValue = "20", required = false) int size
    ) {
        try {
            List<Message> messages = roomService.getMessagesByRoomId(roomId, page, size);
            return ResponseEntity.ok(messages);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
