package com.chatapp.services;

import com.chatapp.entities.Message;
import com.chatapp.payload.MessageRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ChatService {

    Message sendMessage(String roomId, MessageRequest request);
    Message sendFileMessage(String roomId, String sender, MultipartFile file) throws IOException;
}
