package com.chatapp.services.impl;

import com.chatapp.entities.Message;
import com.chatapp.entities.Room;
import com.chatapp.repositories.RoomRepository;
import com.chatapp.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;

    @Autowired
    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public Room createRoom(String roomId) {
        if (roomRepository.findByRoomId(roomId) != null) {
            throw new RuntimeException("Room already exists!");
        }
        Room room = new Room();
        room.setRoomId(roomId);
        return roomRepository.save(room);
    }

    @Override
    public Room getRoomByRoomId(String roomId) {
        Room room = roomRepository.findByRoomId(roomId);
        if (room == null) {
            throw new RuntimeException("Room not found!");
        }
        return room;
    }

    /*
    @Override
    public List<Message> getMessagesByRoomId(String roomId, int page, int size) {
        Room room = roomRepository.findByRoomId(roomId);
        if (room != null) {
            return room.getMessages().stream()
                    .skip(page * size)
                    .limit(size)
                    .collect(Collectors.toList());
        } else {
            throw new RuntimeException("room not found !!");
        }
    }
    */
    @Override
    public List<Message> getMessagesByRoomId(String roomId, int page, int size) {
        Room room = roomRepository.findByRoomId(roomId);
        if (room == null) {
            throw new RuntimeException("Room not found!");
        }
        List<Message> messages = room.getMessages();
        int start = Math.max(0, messages.size() - (page + 1) * size);
        int end = Math.min(messages.size(), start + size);
        return messages.subList(start, end);
    }
}
