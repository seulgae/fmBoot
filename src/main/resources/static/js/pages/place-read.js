document.addEventListener("DOMContentLoaded", () => {
  const thumbs = document.querySelectorAll("[data-gallery-thumb]");
  const main = document.getElementById("mainImg");
  const dateInput = document.getElementById("dateSet");
  const placeId = document.getElementById("p_no")?.value;
  const selectedTime = document.getElementById("time");
  const reserveButton = document.getElementById("btnReservation");
  const slots = Array.from(document.querySelectorAll("[data-slot]"));

  const resetSlots = () => slots.forEach((slot) => slot.classList.remove("is-disabled", "is-selected"));

  const markTodayUnavailable = () => {
    const today = new Date();
    const date = `${today.getFullYear()}-${String(today.getMonth() + 1).padStart(2, "0")}-${String(today.getDate()).padStart(2, "0")}`;
    if (dateInput?.value !== date) return;

    const current = `${String(today.getHours()).padStart(2, "0")}:${String(today.getMinutes()).padStart(2, "0")}`;
    slots.forEach((slot) => {
      if (current > slot.dataset.ends) {
        slot.classList.add("is-disabled");
      }
    });
  };

  const loadReservedSlots = async () => {
    if (!dateInput || !placeId) return;
    resetSlots();
    markTodayUnavailable();

    const response = await fetch("/payment/rserveCheck", {
      method: "POST",
      headers: { "Content-Type": "application/x-www-form-urlencoded" },
      body: new URLSearchParams({ r_date: dateInput.value, p_no: placeId }),
    });

    const reserved = await response.json();
    slots.forEach((slot) => {
      if (reserved.includes(slot.dataset.slot)) {
        slot.classList.add("is-disabled");
      }
    });
  };

  thumbs.forEach((thumb) => {
    thumb.addEventListener("click", () => FM.setPreview(main, thumb.dataset.galleryThumb));
  });

  slots.forEach((slot) => {
    slot.addEventListener("click", () => {
      if (slot.classList.contains("is-disabled")) {
        window.alert("선택할 수 없는 시간입니다.");
        return;
      }
      slots.forEach((item) => item.classList.remove("is-selected"));
      slot.classList.add("is-selected");
      selectedTime.value = slot.dataset.slot;
    });
  });

  dateInput?.addEventListener("change", loadReservedSlots);
  reserveButton?.addEventListener("click", () => {
    if (!document.getElementById("loginCheck")?.value) {
      window.location.href = "/login/login";
      return;
    }
    if (!dateInput?.value || !selectedTime?.value) {
      window.alert("예약 날짜와 시간을 선택해 주세요.");
      return;
    }
    document.getElementById("frmReservation")?.submit();
  });

  loadReservedSlots();
});
