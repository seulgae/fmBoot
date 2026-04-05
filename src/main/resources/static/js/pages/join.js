document.addEventListener("DOMContentLoaded", () => {
  const joinForm = document.getElementById("joinFrm");
  const joinButton = document.getElementById("joinBtn");
  const idInput = document.getElementById("m_id");
  const phoneInput = document.getElementById("m_phone");
  const levelInputs = document.querySelectorAll("input[name='m_level']");
  const managerFields = document.querySelectorAll(".manager-only");
  const postcodeButton = document.getElementById("zipBtn");
  const idMessage = document.getElementById("idCheck");
  const pwMessage = document.getElementById("pwCheck");

  const updateRoleState = () => {
    const level = document.querySelector("input[name='m_level']:checked")?.value;
    managerFields.forEach((element) => {
      element.hidden = level !== "2";
    });
  };

  const updatePasswordMessage = () => {
    const password = document.getElementById("m_pw")?.value;
    const confirmPassword = document.getElementById("m_pw_re")?.value;
    if (!password || !confirmPassword) {
      pwMessage.textContent = "비밀번호를 두 번 입력해 주세요.";
      pwMessage.className = "form-message";
      return false;
    }
    const matches = password === confirmPassword;
    pwMessage.textContent = matches ? "비밀번호가 일치합니다." : "비밀번호가 일치하지 않습니다.";
    pwMessage.className = matches ? "form-message is-success" : "form-message is-error";
    return matches;
  };

  const checkId = async () => {
    const value = idInput?.value?.trim();
    if (!value || value.length < 5) {
      idMessage.textContent = "아이디는 5자 이상이어야 합니다.";
      idMessage.className = "form-message is-error";
      return false;
    }

    const response = await fetch(`/login/idCheck?m_id=${encodeURIComponent(value)}`);
    const result = await response.text();
    const available = result === "0";
    idMessage.textContent = available ? "사용 가능한 아이디입니다." : "이미 사용 중인 아이디입니다.";
    idMessage.className = available ? "form-message is-success" : "form-message is-error";
    return available;
  };

  levelInputs.forEach((input) => input.addEventListener("change", updateRoleState));
  updateRoleState();

  idInput?.addEventListener("blur", checkId);
  document.getElementById("m_pw")?.addEventListener("input", updatePasswordMessage);
  document.getElementById("m_pw_re")?.addEventListener("input", updatePasswordMessage);
  phoneInput?.addEventListener("input", () => {
    phoneInput.value = FM.formatPhone(phoneInput.value);
  });

  postcodeButton?.addEventListener("click", () => {
    if (!window.daum?.Postcode) {
      window.alert("주소 검색 스크립트를 불러오지 못했습니다.");
      return;
    }
    new daum.Postcode({
      oncomplete(data) {
        document.getElementById("m_zip").value = data.zonecode;
        document.getElementById("m_addr1").value = data.roadAddress;
        document.getElementById("m_addr2").focus();
      },
    }).open();
  });

  joinButton?.addEventListener("click", async () => {
    const validId = await checkId();
    const validPassword = updatePasswordMessage();
    const required = ["m_name", "m_phone", "m_email", "m_zip", "m_addr1", "m_addr2"];
    const missing = required.some((id) => !document.getElementById(id)?.value?.trim());
    const terms = ["uChk1", "uChk2", "uChk3"].every((id) => document.getElementById(id)?.checked);
    const isManager = document.querySelector("input[name='m_level']:checked")?.value === "2";
    const managerMissing = isManager && ["m_bank", "m_account"].some((id) => !document.getElementById(id)?.value?.trim());

    if (!validId || !validPassword || missing || !terms || managerMissing) {
      window.alert("입력값과 약관 동의를 다시 확인해 주세요.");
      return;
    }

    joinForm?.submit();
  });
});
