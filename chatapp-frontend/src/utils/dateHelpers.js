export const formatChatDate = (timestamp) => {
  const messageDate = new Date(timestamp);
  const today = new Date();

  const isToday =
    messageDate.getDate() === today.getDate() &&
    messageDate.getMonth() === today.getMonth() &&
    messageDate.getFullYear() === today.getFullYear();

  if (isToday) return "Today";

  return messageDate.toLocaleDateString(undefined, {
    year: "numeric",
    month: "long",
    day: "numeric",
  });
};
