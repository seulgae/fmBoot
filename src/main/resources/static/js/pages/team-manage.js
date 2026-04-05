document.addEventListener("DOMContentLoaded", async () => {
  const createButton = document.getElementById("teamCreateBtn");
  const loginCheck = document.getElementById("loginCheck")?.value;

  createButton?.addEventListener("click", () => {
    if (!loginCheck) {
      window.location.href = "/login/login";
      return;
    }
    FM.popup("/teammanage/teamcreate", { width: 860, height: 860, name: "teamCreate" });
  });

  const rows = Array.from(document.querySelectorAll("[data-team-row]"));
  await Promise.all(rows.map(async (row) => {
    const teamNo = row.dataset.teamRow;
    const response = await fetch(`/teammanage/countmatch?t_no=${teamNo}`);
    const [id, all, win, draw, lose] = await response.json();
    row.querySelector("[data-stat='all']").textContent = all;
    row.querySelector("[data-stat='win']").textContent = win;
    row.querySelector("[data-stat='draw']").textContent = draw;
    row.querySelector("[data-stat='lose']").textContent = lose;
  }));
});
