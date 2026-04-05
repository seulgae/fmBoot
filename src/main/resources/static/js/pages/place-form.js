document.addEventListener("DOMContentLoaded", () => {
  const input = document.getElementById("addr");
  input?.addEventListener("click", () => {
    if (!window.daum?.Postcode) {
      window.alert("주소 검색 스크립트를 불러오지 못했습니다.");
      return;
    }
    new daum.Postcode({
      oncomplete(data) {
        input.value = data.roadAddress;
      },
    }).open();
  });
});
