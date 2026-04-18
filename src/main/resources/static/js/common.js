window.FM = (() => {
  const popup = (url, options = {}) => {
    const settings = {
      name: options.name || "fmPopup",
      width: options.width || 900,
      height: options.height || 700,
      top: options.top || 40,
      left: options.left || 40,
    };

    const features = [
      `width=${settings.width}`,
      `height=${settings.height}`,
      `top=${settings.top}`,
      `left=${settings.left}`,
      "status=no",
      "menubar=no",
      "toolbar=no",
      "resizable=yes",
      "scrollbars=yes",
    ].join(",");

    window.open(url, settings.name, features);
  };

  const confirmNavigate = (message, url) => {
    if (window.confirm(message)) {
      window.location.href = url;
    }
  };

  const goBack = () => window.history.back();

  const setPreview = (img, src) => {
    if (img && src) {
      img.src = src;
    }
  };

  const formatPhone = (value) =>
    value
      .replace(/[^0-9]/g, "")
      .replace(/(^02|^0505|^1[0-9]{3}|^0[0-9]{2})([0-9]+)?([0-9]{4})$/, "$1-$2-$3")
      .replace("--", "-");

  const bindNavToggle = () => {
    const toggle = document.querySelector("[data-nav-toggle]");
    if (!toggle) return;
    const navId = toggle.getAttribute("aria-controls");
    const nav = navId ? document.getElementById(navId) : null;
    if (!nav) return;

    const setOpen = (open) => {
      toggle.setAttribute("aria-expanded", String(open));
      toggle.setAttribute("aria-label", open ? "메뉴 닫기" : "메뉴 열기");
      nav.classList.toggle("is-open", open);
    };

    toggle.addEventListener("click", () => {
      setOpen(toggle.getAttribute("aria-expanded") !== "true");
    });

    // 메뉴 링크 클릭 시 자동 닫기
    nav.querySelectorAll("a").forEach((link) => {
      link.addEventListener("click", () => setOpen(false));
    });

    // 데스크톱 폭으로 확장되면 상태 리셋
    const mql = window.matchMedia("(min-width: 821px)");
    const onWide = (e) => { if (e.matches) setOpen(false); };
    if (mql.addEventListener) mql.addEventListener("change", onWide);
    else mql.addListener(onWide);
  };

  const bindDeclarativeActions = () => {
    bindNavToggle();
    document.querySelectorAll("[data-popup-url]").forEach((element) => {
      element.addEventListener("click", () => {
        popup(element.dataset.popupUrl, {
          width: Number(element.dataset.popupWidth || 900),
          height: Number(element.dataset.popupHeight || 700),
          name: element.dataset.popupName || "fmPopup",
        });
      });
    });

    document.querySelectorAll("[data-confirm-url]").forEach((element) => {
      element.addEventListener("click", (event) => {
        event.preventDefault();
        confirmNavigate(
          element.dataset.confirmMessage || "계속 진행하시겠습니까?",
          element.dataset.confirmUrl
        );
      });
    });

    document.querySelectorAll("[data-go-back]").forEach((element) => {
      element.addEventListener("click", goBack);
    });
  };

  const registerServiceWorker = () => {
    if (!("serviceWorker" in navigator)) return;
    // localhost + https 외에는 SW 등록 불가 (브라우저 정책)
    if (location.protocol !== "https:" && location.hostname !== "localhost" && location.hostname !== "127.0.0.1") return;
    window.addEventListener("load", () => {
      navigator.serviceWorker.register("/service-worker.js", { scope: "/" }).catch(() => {
        /* 조용히 실패 — 오프라인 지원이 없을 뿐 기능엔 영향 없음 */
      });
    });
  };

  document.addEventListener("DOMContentLoaded", bindDeclarativeActions);
  registerServiceWorker();

  return { popup, confirmNavigate, goBack, setPreview, formatPhone };
})();
