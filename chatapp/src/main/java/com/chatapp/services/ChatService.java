package com.chatapp.services;

import com.chatapp.entities.Message;
import com.chatapp.payload.MessageRequest;

public interface ChatService {

    Message sendMessage(String roomId, MessageRequest request);

}
