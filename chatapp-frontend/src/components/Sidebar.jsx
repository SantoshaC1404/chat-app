const Sidebar = ({ conversations, selectedUser, setSelectedUser }) => {
  return (
    <div className="w-80 bg-dark p-4 text-white h-screen overflow-y-auto">
      <h2 className="text-xl font-bold mb-4">Chatapp</h2>
      {/* Search Placeholder */}
      {/* <input
        type="text"
        placeholder="Search here.."
        className="w-full p-2 mb-4 rounded bg-gray-700 focus:outline-none"
      /> */}

      <div className="space-y-2">
        {conversations.map((conv, index) => (
          <div
            key={index}
            onClick={() => setSelectedUser(conv)}
            className={`flex items-center gap-3 p-3 rounded-lg cursor-pointer transition-colors duration-150  ${
              selectedUser?.userName === conv.userName
                ? "bg-[#0ea5ff] text-white"
                : "hover:bg-[#0f2a3f]"
            }`}
          >
            <img
              src={`https://avatar.iran.liara.run/public/${index + 10}`}
              className="w-12 h-12 rounded-full border-2 border-transparent"
              alt="avatar"
            />
            <div className="flex-1">
              <p className="font-semibold text-sm">{conv.userName}</p>
              <p className="text-xs text-gray-300">A random message</p>
            </div>
            <div className="text-xs text-gray-300">2:45 PM</div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default Sidebar;
