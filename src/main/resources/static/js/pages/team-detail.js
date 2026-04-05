document.addEventListener("DOMContentLoaded", () => {
  const teamNo = document.getElementById("teamNo")?.value;
  const loginCheck = document.getElementById("loginCheck")?.value;

  document.getElementById("teamUpdateBtn")?.addEventListener("click", () => {
    if (!loginCheck) return window.location.assign("/login/login");
    FM.popup(`/teammanage/teamupdate?t_no=${teamNo}`, { width: 860, height: 860, name: "teamUpdate" });
  });

  document.getElementById("teamMatchBtn")?.addEventListener("click", () => {
    if (!loginCheck) return window.location.assign("/login/login");
    FM.popup(`/teammanage/addmatch?t_no=${teamNo}`, { width: 700, height: 640, name: "teamMatch" });
  });

  document.getElementById("teamMemberBtn")?.addEventListener("click", () => {
    if (!loginCheck) return window.location.assign("/login/login");
    FM.popup(`/teammanage/addmember?t_no=${teamNo}`, { width: 640, height: 680, name: "teamMember" });
  });

  document.getElementById("teamProfileBtn")?.addEventListener("click", () => {
    FM.popup(`/teammanage/teamprofile?t_no=${teamNo}`, { width: 520, height: 320, name: "teamProfile" });
  });

  document.getElementById("teamDeleteBtn")?.addEventListener("click", () => {
    if (!loginCheck) return window.location.assign("/login/login");
    FM.confirmNavigate("팀을 삭제하시겠습니까?", `/teammanage/teamdelete?t_no=${teamNo}`);
  });
});
