package com.chatapp.services.impl;

import com.chatapp.entities.Message;
import com.chatapp.entities.Room;
import com.chatapp.payload.MessageRequest;
import com.chatapp.repositories.RoomRepository;
import com.chatapp.services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.UUID;

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

    @Override
    public Message sendFileMessage(String roomId, String sender, MultipartFile file) throws IOException {
        Room room = roomRepository.findByRoomId(roomId);
        if (room == null) {
            throw new RuntimeException("Room not found!");
        }

        // ✅ Use absolute path (e.g., in project root or fixed location)
        String uploadDirPath = System.getProperty("user.dir") + "/uploads"; // or hardcode a full path
        File uploadDir = new File(uploadDirPath);

        // ✅ Create uploads folder if it doesn't exist
        if (!uploadDir.exists()) {
            boolean created = uploadDir.mkdirs();
            if (!created) {
                throw new IOException("Failed to create upload directory: " + uploadDirPath);
            }
        }

        // ✅ Save the file
        String originalFilename = file.getOriginalFilename();
        String uniqueFilename = UUID.randomUUID() + "_" + originalFilename;
        File destinationFile = new File(uploadDir, uniqueFilename);
        file.transferTo(destinationFile); // <-- this line was previously failing

        // ✅ Create and save message
        Message message = new Message();
        message.setSender(sender);
        message.setContent("/uploads/" + uniqueFilename); // just an example if you're showing the file in UI
        message.setTimeStamp(LocalDateTime.now());

        room.getMessages().add(message);
        roomRepository.save(room);

        return message;
    }

}
