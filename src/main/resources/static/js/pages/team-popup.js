document.addEventListener("DOMContentLoaded", () => {
  const form = document.querySelector("form[data-team-popup]");
  document.getElementById("teamPopupSubmit")?.addEventListener("click", () => form?.submit());
});
