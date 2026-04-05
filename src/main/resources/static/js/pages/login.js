document.addEventListener("DOMContentLoaded", () => {
  const loginButton = document.getElementById("loginBtn");
  const loginForm = document.getElementById("loginFrm");
  const findPopupButton = document.getElementById("loginFind");
  const findIdButton = document.getElementById("btnFindId");
  const findPwButton = document.getElementById("btnFindPw");
  const changeButton = document.getElementById("btnChange");

  loginButton?.addEventListener("click", async () => {
    const id = document.getElementById("m_id")?.value?.trim();
    const password = document.getElementById("m_pw")?.value?.trim();

    const response = await fetch("/login/loginCheck", {
      method: "POST",
      headers: { "Content-Type": "application/x-www-form-urlencoded" },
      body: new URLSearchParams({ m_id: id || "", m_pw: password || "" }),
    });

    const result = await response.text();
    if (result === "1") {
      loginForm?.submit();
      return;
    }

    window.alert("아이디 또는 비밀번호를 다시 확인해 주세요.");
  });

  findPopupButton?.addEventListener("click", () => {
    FM.popup("/login/findId", { width: 760, height: 420, name: "findAccount" });
  });

  findIdButton?.addEventListener("click", () => {
    const checked = document.querySelector("input[name='findId']:checked")?.value;
    const value = document.getElementById("findValue")?.value?.trim();
    if (!value) {
      window.alert("검색 값을 입력해 주세요.");
      return;
    }
    window.location.href = `/login/wannaGetId/${checked}/${value}`;
  });

  findPwButton?.addEventListener("click", () => {
    const id = document.getElementById("pw_id")?.value?.trim();
    const email = document.getElementById("pw_email")?.value?.trim();
    if (!id || !email) {
      window.alert("아이디와 이메일을 모두 입력해 주세요.");
      return;
    }
    window.location.href = `/login/wannaGetPw/${id}/${email}`;
  });

  changeButton?.addEventListener("click", () => {
    const password = document.getElementById("m_pw")?.value;
    const passwordConfirm = document.getElementById("m_pw1")?.value;
    if (password !== passwordConfirm) {
      window.alert("비밀번호가 일치하지 않습니다.");
      return;
    }
    document.getElementById("frmChange")?.submit();
  });
});
