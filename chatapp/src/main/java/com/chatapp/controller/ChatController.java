package com.chatapp.controller;

import com.chatapp.entities.Message;
import com.chatapp.payload.MessageRequest;
import com.chatapp.services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class ChatController {

    @Autowired
    private ChatService chatService;
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/sendMessage/{roomId}")
    @SendTo("/topic/room/{roomId}")
    public Message sendMessage(
            @DestinationVariable String roomId,
            @RequestBody MessageRequest request) {
        return chatService.sendMessage(roomId, request);
    }

    @PostMapping("/upload")
    public Message uploadFile(
            @RequestParam("roomId") String roomId,
            @RequestParam("sender") String sender,
            @RequestParam("file") MultipartFile file) throws IOException {

        Message msg = chatService.sendFileMessage(roomId, sender, file);

        // After saving, publish the message manually to WebSocket subscribers
        simpMessagingTemplate.convertAndSend(
                "/topic/room/" + roomId,
                msg
        );

        return msg;
    }

}
