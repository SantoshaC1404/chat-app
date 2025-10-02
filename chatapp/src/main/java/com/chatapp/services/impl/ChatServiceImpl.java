package com.chatapp.services.impl;

import com.chatapp.entities.Message;
import com.chatapp.entities.Room;
import com.chatapp.payload.MessageRequest;
import com.chatapp.repositories.RoomRepository;
import com.chatapp.services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ChatServiceImpl implements ChatService {

    private final RoomRepository roomRepository;

    @Autowired
    public ChatServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public Message sendMessage(String roomId, MessageRequest request) {
        Room room = roomRepository.findByRoomId(request.getRoomId());
        Message message = new Message();
        message.setContent(request.getContent());
        message.setSender(request.getSender());
        message.setTimeStamp(LocalDateTime.now());
        if (room != null) {
            room.getMessages().add(message);
            roomRepository.save(room);
        } else {
            throw new RuntimeException("room not found !!");
        }
        return message;
    }
}
