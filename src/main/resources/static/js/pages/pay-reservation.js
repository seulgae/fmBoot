document.addEventListener("DOMContentLoaded", () => {
  const terms = ["chk1", "chk2"].map((id) => document.getElementById(id));
  const payButton = document.getElementById("payBtn");
  const payForm = document.getElementById("frmPay");

  const read = (id) => document.getElementById(id)?.value;
  const syncButton = () => {
    payButton.disabled = !terms.every((item) => item?.checked);
  };

  terms.forEach((item) => item?.addEventListener("change", syncButton));
  syncButton();

  payButton?.addEventListener("click", () => {
    if (!window.IMP) {
      window.alert("결제 모듈을 불러오지 못했습니다.");
      return;
    }

    IMP.init("imp40464124");
    IMP.request_pay({
      pg: "kakaopay.TC0ONETIME",
      pay_method: "card",
      merchant_uid: `merchant_${Date.now()}`,
      name: read("payPlaceName"),
      amount: Number(read("payPrice")),
      buyer_email: read("payEmail"),
      buyer_name: read("payName"),
      buyer_tel: read("payPhone"),
      buyer_addr: "",
      buyer_postcode: "",
      digital: false,
      app_scheme: "",
    }, async (rsp) => {
      if (!rsp.success) {
        window.alert("결제에 실패했습니다.");
        return;
      }

      await fetch(`/payment/pay_reservation.do/${rsp.imp_uid}`, {
        method: "POST",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body: new URLSearchParams({
          p_no: read("payPlaceNo"),
          pay_price: read("payPrice"),
          m_id: read("payMemberId"),
        }),
      });

      payForm?.submit();
    });
  });
});
