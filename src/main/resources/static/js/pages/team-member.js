document.addEventListener("DOMContentLoaded", () => {
  const findButton = document.getElementById("btnFind");
  const resultBox = document.getElementById("memberList");
  const selectedBody = document.getElementById("selectedMembers");
  const selected = new Set();

  const renderSelected = () => {
    selectedBody.innerHTML = "";
    Array.from(selected).forEach((memberId) => {
      const row = document.createElement("tr");
      row.innerHTML = `<td>${memberId}</td><td><button type=\"button\" class=\"btn btn--secondary\" data-remove=\"${memberId}\">제거</button></td>`;
      selectedBody.appendChild(row);
    });

    selectedBody.querySelectorAll("[data-remove]").forEach((button) => {
      button.addEventListener("click", () => {
        selected.delete(button.dataset.remove);
        renderSelected();
      });
    });

    document.getElementById("str_member").value = `${Array.from(selected).join(" ")} `;
  };

  findButton?.addEventListener("click", async () => {
    const keyword = document.getElementById("findMember")?.value?.trim();
    if (!keyword) {
      window.alert("검색할 아이디를 입력해 주세요.");
      return;
    }

    const response = await fetch(`/teammanage/findMember?m_id=${encodeURIComponent(keyword)}`);
    resultBox.innerHTML = await response.text();
    resultBox.querySelectorAll("[data-member-id]").forEach((button) => {
      button.addEventListener("click", () => {
        selected.add(button.dataset.memberId);
        renderSelected();
      });
    });
  });

  document.getElementById("submitMembers")?.addEventListener("click", () => {
    if (!selected.size) {
      window.alert("추가할 팀원을 선택해 주세요.");
      return;
    }
    document.getElementById("frmAddMember")?.submit();
  });
});
