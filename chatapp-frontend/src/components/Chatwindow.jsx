import { useEffect, useRef } from "react";
import { MdAttachFile, MdSend, MdImage } from "react-icons/md";
import { FiInfo } from "react-icons/fi";
import { timeAgo } from "../utils/timeAgo";

const ChatWindow = ({
  messages,
  currentUser,
  input,
  setInput,
  sendMessage,
}) => {
  const chatBoxRef = useRef();
  const fileInputRef = useRef();

  useEffect(() => {
    chatBoxRef.current?.scrollTo({
      top: chatBoxRef.current.scrollHeight,
      behavior: "smooth",
    });
  }, [messages]);

  return (
    <main className="flex-1 h-screen flex flex-col bg-[#f6f8fb]">
      {/* Chat Header */}
      <div className="px-8 py-4 bg-white">
        <div className="flex items-center justify-between">
          <div className="flex items-center gap-4">
            <img
              src={`https://avatar.iran.liara.run/public/32`}
              alt="avatar"
              className="w-14 h-14 rounded-full"
            />
            <div>
              <h2 className="text-xl text-black flex items-center gap-3">
                {currentUser || "No user"}
                <span className="w-3 h-3 bg-green-400 rounded-full inline-block" />
              </h2>
              <p className="text-sm text-gray-500">Online</p>
            </div>
          </div>

          <div className="w-10 h-10 rounded-full bg-white/30 border border-gray-200 flex items-center justify-center text-gray-600">
            <FiInfo size={18} />
          </div>
        </div>

        {/* thin divider line under header */}
        <div className="mt-4 border-t border-gray-100" />
      </div>

      {/* Messages */}
      <div ref={chatBoxRef} className="flex-1 px-8 py-6 overflow-y-auto">
        <div className="max-w-2xl mx-auto space-y-6">
          {messages.map((msg, idx) => {
            const imageUrl =
              msg.image ||
              msg.imageUrl ||
              (typeof msg.content === "string" &&
              /(https?:\/\/.*\.(?:png|jpg|jpeg|gif|webp))|(data:image\/)/i.test(
                msg.content
              )
                ? msg.content
                : null);
            const hasImage = Boolean(imageUrl);

            return (
              <div
                key={idx}
                className={`flex items-end ${
                  msg.sender === currentUser ? "justify-end" : "justify-start"
                }`}
              >
                {/* incoming: avatar on left with time below */}
                {msg.sender !== currentUser && (
                  <div className="flex flex-col items-center mr-3 self-start translate-y-6">
                    <img
                      src={`https://avatar.iran.liara.run/public/${idx + 20}`}
                      className="w-8 h-8 rounded-full shadow-sm"
                    />
                    <span className="text-xs text-gray-400 mt-2">
                      {new Date(msg.timeStamp).toLocaleTimeString([], {
                        hour: "2-digit",
                        minute: "2-digit",
                        hour12: true,
                      })}
                    </span>
                  </div>
                )}

                <div
                  className={`p-2 rounded-lg shadow-md mb-4 ${
                    hasImage ? "max-w-2xl" : "max-w-md"
                  } ${
                    msg.sender === currentUser
                      ? "bg-[#5b6bff] text-white rounded-br-none"
                      : "bg-white text-gray-900 rounded-bl-none"
                  }`}
                >
                  {/* message text (if content is not the image url itself) */}
                  {!(hasImage && imageUrl === msg.content) && (
                    <p className="mb-2">{msg.content}</p>
                  )}

                  {/* image (rendered below the text) */}
                  {hasImage && (
                    <img
                      src={imageUrl}
                      alt="sent"
                      className="mt-3 w-full max-w-xs md:max-w-md lg:max-w-2xl rounded-md object-cover"
                    />
                  )}
                </div>

                {/* outgoing: avatar on right with time below */}
                {msg.sender === currentUser && (
                  <div className="flex flex-col items-center ml-3 justify-end translate-y-4 mb-2">
                    <img
                      src={`https://avatar.iran.liara.run/public/33`}
                      className="w-8 h-8 rounded-full shadow-sm"
                    />
                    <span className="text-xs text-gray-400 mt-2">
                      {new Date(msg.timeStamp).toLocaleTimeString([], {
                        hour: "2-digit",
                        minute: "2-digit",
                        hour12: true,
                      })}
                    </span>
                  </div>
                )}
              </div>
            );
          })}
        </div>
      </div>

      {/* Input Box */}
      <div className="px-8 py-4 border-t bg-white">
        <div className="max-w-2xl mx-auto flex items-center gap-3">
          {/* image select button + hidden file input (placed next to send) */}
          <>
            <input
              ref={fileInputRef}
              type="file"
              accept="image/*"
              onChange={(e) => {
                const file = e.target.files && e.target.files[0];
                if (file) {
                  console.log("Selected image:", file);
                }
                e.target.value = null;
              }}
              className="hidden"
            />

            <button
              onClick={() => fileInputRef.current?.click()}
              aria-label="Attach image"
              className="p-3 bg-[#1da1f2] rounded-full text-white cursor-pointer"
            >
              <MdImage size={20} />
            </button>
          </>
          <input
            value={input}
            onChange={(e) => setInput(e.target.value)}
            onKeyDown={(e) => e.key === "Enter" && sendMessage()}
            type="text"
            placeholder="Send a message"
            className="flex-1 p-3 rounded-full bg-[#f1f3f8] focus:outline-none text-gray-900 placeholder-gray-500"
          />
          <button
            onClick={sendMessage}
            className="p-3 bg-[#1da1f2] rounded-full text-white cursor-pointer"
          >
            <MdSend size={20} />
          </button>
        </div>
      </div>
    </main>
  );
};
export default ChatWindow;
