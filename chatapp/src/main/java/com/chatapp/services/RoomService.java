package com.chatapp.services;

import com.chatapp.entities.Message;
import com.chatapp.entities.Room;

import java.util.List;

public interface RoomService {

    Room createRoom(String roomId);

    Room getRoomByRoomId(String roomId);

    List<Message> getMessagesByRoomId(String roomId, int page, int size);
}
