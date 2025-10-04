const UserProfile = ({ user, media, onLogout }) => {
  return (
    <aside className="w-1/4 bg-[#071a2f] p-8 text-white h-screen">
      <div className="flex flex-col items-center">
        <img
          src="https://avatar.iran.liara.run/public/43"
          className="w-28 h-28 rounded-full border-4 border-white/10"
        />
        <h2 className="text-2xl font-bold mt-4">{user.userName}</h2>
        <p className="text-sm text-gray-300 mt-2">
          Lorem ipsum is placeholder text commonly used in ..
        </p>
      </div>

      <div className="mt-8">
        <h3 className="text-lg font-semibold mb-4">Media</h3>
        <div className="grid grid-cols-3 gap-3">
          {media.map((img, i) => (
            <img
              key={i}
              src={img}
              alt="media"
              className="w-full h-20 object-cover rounded-md"
            />
          ))}
        </div>
      </div>

      <div className="mt-8">
        <button
          onClick={onLogout}
          className="w-full py-3 bg-[#1da1f2] hover:bg-[#0d8ae6] rounded-full text-white font-semibold"
        >
          Logout
        </button>
      </div>
    </aside>
  );
};
export default UserProfile;
