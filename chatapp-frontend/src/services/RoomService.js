import { httpClient } from "../config/AxiosHelper";

export const createRoomApi = async (roomDetail) => {
  const response = await httpClient.post(`/api/v1/rooms`, roomDetail, {
    headers: {
      "Content-Type": "application/json",
    },
  });
  return response.data;
};

export const joinChatApi = async (roomId) => {
  try {
    const response = await httpClient.get(`/api/v1/rooms/${roomId}`);
    return response.data;
  } catch (error) {
    if (error.response && error.response.status === 404) {
      throw new Error("Room not found");
    }
    throw error;
  }
};

export const getMessagess = async (roomId, size = 50, page = 0) => {
  const response = await httpClient.get(
    `/api/v1/rooms/${roomId}/messages?size=${size}&page=${page}`
  );
  return response.data;
};

async function joinChat() {
  if (validateForm()) {
    try {
      const room = await joinChatApi(detail.roomId);
      toast.success("Joined..");
      setCurrentUser(detail.userName);
      setRoomId(room.roomId);
      setConnected(true);
      navigate("/chat");
    } catch (error) {
      if (error.message === "Room not found") {
        toast.error("Room not found");
      } else {
        toast.error("Error in joining room");
      }
      console.log(error);
    }
  }
}
