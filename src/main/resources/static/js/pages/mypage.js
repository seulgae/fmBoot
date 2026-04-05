document.addEventListener("DOMContentLoaded", () => {
  document.getElementById("profileOpen")?.addEventListener("click", () => {
    FM.popup("/mypage/addphoto", { width: 560, height: 280, name: "profilePhoto" });
  });

  document.getElementById("reservationOpen")?.addEventListener("click", () => {
    FM.popup("/mypage/reservation_list", { width: 1040, height: 640, name: "reservationList" });
  });
});
