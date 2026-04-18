/**
 * FM 매니저 공통 스크립트
 *
 * 제공 유틸:
 *   - popup(url, opt)              : 고정 규격 서브 창 열기
 *   - confirmNavigate(msg, url)    : 확인창 후 이동
 *   - goBack()                     : 뒤로가기
 *   - setPreview(img, src)         : 이미지 src 교체
 *   - formatPhone(value)           : 전화번호 문자열 하이픈 포맷
 *   - toast(msg, variant)          : 가벼운 스낵바 알림
 *
 * 선언형(data-*) 바인딩:
 *   - [data-nav-toggle]            : 모바일 햄버거 네비 열기/닫기
 *   - [data-popup-url]             : 클릭 시 popup() 호출
 *   - [data-confirm-url]           : 클릭 시 confirmNavigate() 호출
 *   - [data-go-back]               : 클릭 시 history.back
 *   - [data-file-preview]          : 파일 선택 시 미리보기 이미지 갱신
 *   - [data-phone-input]           : 전화번호 필드 자동 포맷
 *
 * 서비스 워커:
 *   - localhost / https 환경에서만 /service-worker.js 등록 (PWA)
 *
 * 확장 방식:
 *   페이지 단위 스크립트는 /js/pages/ 아래에서 FM 유틸을 사용.
 */
window.FM = (() => {

  /* ==========================================================
     1. 유틸 함수
     ========================================================== */

  /** 고정 규격 서브 창(팝업) 열기 */
  const popup = (url, options = {}) => {
    const settings = {
      name:   options.name   || "fmPopup",
      width:  options.width  || 900,
      height: options.height || 700,
      top:    options.top    || 40,
      left:   options.left   || 40,
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

  /** 확인(Confirm) 후 해당 URL 로 이동 */
  const confirmNavigate = (message, url) => {
    if (window.confirm(message)) {
      window.location.href = url;
    }
  };

  /** 브라우저 히스토리 뒤로 */
  const goBack = () => window.history.back();

  /** <img> 요소 src 교체 (미리보기 공용) */
  const setPreview = (img, src) => {
    if (img && src) img.src = src;
  };

  /** 숫자만 추출 후 한국식 전화번호 하이픈 포맷 */
  const formatPhone = (value) =>
    String(value || "")
      .replace(/[^0-9]/g, "")
      .replace(/(^02|^0505|^1[0-9]{3}|^0[0-9]{2})([0-9]+)?([0-9]{4})$/, "$1-$2-$3")
      .replace("--", "-");

  /** 가벼운 스낵바 토스트 (성공/경고/오류 3 종) */
  const toast = (message, variant = "info") => {
    if (!message) return;
    const host = ensureToastHost();
    const el = document.createElement("div");
    el.className = `fm-toast fm-toast--${variant}`;
    el.textContent = message;
    host.appendChild(el);
    // 등장 애니메이션 후 3초 뒤 제거
    requestAnimationFrame(() => el.classList.add("is-on"));
    setTimeout(() => {
      el.classList.remove("is-on");
      setTimeout(() => el.remove(), 250);
    }, 2800);
  };

  /**
   * 무한 스크롤 바인딩 유틸.
   *
   * 동작 흐름:
   *   1. `sentinelSelector` 를 관찰하다가 뷰포트에 들어오면
   *   2. `fetchNext(currentCount)` 결과를 HTML 로 파싱
   *   3. 응답 문서에서 `itemSelector` 요소 수가 현재보다 많을 때
   *      초과 항목만 리스트에 append
   *   4. 더 이상 새 항목이 없으면 센티넬을 is-done 처리하고 관찰 해제
   *
   * 옵션:
   *   - listSelector     : 항목들이 추가될 컨테이너
   *   - itemSelector     : 개별 카드/행 셀렉터
   *   - sentinelSelector : 하단 센티넬 요소 셀렉터
   *   - fetchNext        : (currentCount) => Promise<Response>
   *   - rootMargin       : 미리 불러올 마진(기본 "240px")
   */
  const infiniteList = ({
    listSelector,
    itemSelector,
    sentinelSelector,
    fetchNext,
    rootMargin = "240px",
  }) => {
    const list = document.querySelector(listSelector);
    const sentinel = document.querySelector(sentinelSelector);
    if (!list || !sentinel || typeof fetchNext !== "function") return;

    let loading = false;
    let done = false;

    const finish = () => {
      done = true;
      sentinel.classList.remove("is-loading");
      sentinel.classList.add("is-done");
      observer.disconnect();
    };

    const loadMore = async () => {
      if (loading || done) return;
      const currentCount = list.querySelectorAll(itemSelector).length;
      loading = true;
      sentinel.classList.add("is-loading");
      try {
        const response = await fetchNext(currentCount);
        if (!response || !response.ok) throw new Error("fetch failed");
        const html = await response.text();
        const doc = new DOMParser().parseFromString(html, "text/html");
        const incoming = doc.querySelectorAll(`${listSelector} ${itemSelector}`);
        if (incoming.length <= currentCount) {
          finish();
          return;
        }
        const fragment = document.createDocumentFragment();
        for (let i = currentCount; i < incoming.length; i++) {
          fragment.appendChild(incoming[i].cloneNode(true));
        }
        list.appendChild(fragment);
      } catch (e) {
        /* 네트워크 실패 시 조용히 종료(다음 스크롤에 재시도 가능하도록 done 처리 안 함) */
      } finally {
        loading = false;
        sentinel.classList.remove("is-loading");
      }
    };

    const observer = new IntersectionObserver((entries) => {
      if (entries.some((e) => e.isIntersecting)) loadMore();
    }, { rootMargin });

    observer.observe(sentinel);
  };

  const ensureToastHost = () => {
    let host = document.getElementById("fm-toast-host");
    if (host) return host;
    host = document.createElement("div");
    host.id = "fm-toast-host";
    host.setAttribute("aria-live", "polite");
    // 인라인 스타일로 외부 CSS 의존성 제거 — 컴포넌트 단독 동작
    Object.assign(host.style, {
      position: "fixed",
      right: "16px",
      bottom: "16px",
      display: "flex",
      flexDirection: "column",
      gap: "8px",
      zIndex: "9999",
      pointerEvents: "none",
    });
    document.body.appendChild(host);
    // 간단한 자체 스타일 (한 번만 주입)
    if (!document.getElementById("fm-toast-style")) {
      const style = document.createElement("style");
      style.id = "fm-toast-style";
      style.textContent = `
        .fm-toast{pointer-events:auto;padding:10px 14px;border-radius:10px;font-size:13.5px;color:#fff;background:#1a1410;box-shadow:0 8px 20px rgba(0,0,0,.2);opacity:0;transform:translateY(8px);transition:opacity .2s,transform .2s;max-width:340px}
        .fm-toast.is-on{opacity:1;transform:translateY(0)}
        .fm-toast--success{background:#16a34a}
        .fm-toast--warning{background:#ea9a26}
        .fm-toast--error{background:#dc2626}
      `;
      document.head.appendChild(style);
    }
    return host;
  };


  /* ==========================================================
     2. 선언형(data-*) 바인딩
     ========================================================== */

  /** 모바일 햄버거 네비 토글 */
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

    // 메뉴 링크를 누르면 드로어 자동 닫힘
    nav.querySelectorAll("a").forEach((link) => {
      link.addEventListener("click", () => setOpen(false));
    });

    // 데스크톱 폭으로 확장되면 강제로 닫힘 처리
    const mql = window.matchMedia("(min-width: 821px)");
    const onWide = (e) => { if (e.matches) setOpen(false); };
    if (mql.addEventListener) mql.addEventListener("change", onWide);
    else mql.addListener(onWide);
  };

  /** 팝업 트리거(data-popup-url) 바인딩 */
  const bindPopupTriggers = () => {
    document.querySelectorAll("[data-popup-url]").forEach((el) => {
      el.addEventListener("click", () => {
        popup(el.dataset.popupUrl, {
          width:  Number(el.dataset.popupWidth  || 900),
          height: Number(el.dataset.popupHeight || 700),
          name:   el.dataset.popupName || "fmPopup",
        });
      });
    });
  };

  /** 확인 후 이동 트리거(data-confirm-url) 바인딩 */
  const bindConfirmLinks = () => {
    document.querySelectorAll("[data-confirm-url]").forEach((el) => {
      el.addEventListener("click", (event) => {
        event.preventDefault();
        confirmNavigate(
          el.dataset.confirmMessage || "계속 진행하시겠습니까?",
          el.dataset.confirmUrl
        );
      });
    });
  };

  /** 뒤로가기 버튼(data-go-back) */
  const bindGoBack = () => {
    document.querySelectorAll("[data-go-back]").forEach((el) => {
      el.addEventListener("click", goBack);
    });
  };

  /** 파일 선택 미리보기(data-file-preview="#imgId") */
  const bindFilePreview = () => {
    document.querySelectorAll("[data-file-preview]").forEach((input) => {
      const targetSel = input.dataset.filePreview;
      const target = targetSel ? document.querySelector(targetSel) : null;
      if (!target) return;
      input.addEventListener("change", (e) => {
        const file = e.target.files && e.target.files[0];
        if (!file) return;
        const reader = new FileReader();
        reader.onload = () => {
          target.hidden = false;
          target.src = reader.result;
        };
        reader.readAsDataURL(file);
      });
    });
  };

  /** 전화번호 필드 자동 포맷(data-phone-input) */
  const bindPhoneInputs = () => {
    document.querySelectorAll("[data-phone-input]").forEach((input) => {
      input.addEventListener("input", (e) => {
        e.target.value = formatPhone(e.target.value);
      });
    });
  };

  /** 모든 선언형 바인딩 초기화 (DOMContentLoaded 시 일괄 호출) */
  const bindDeclarativeActions = () => {
    bindNavToggle();
    bindPopupTriggers();
    bindConfirmLinks();
    bindGoBack();
    bindFilePreview();
    bindPhoneInputs();
  };


  /* ==========================================================
     3. 서비스 워커 등록 (PWA)
     ========================================================== */

  const registerServiceWorker = () => {
    if (!("serviceWorker" in navigator)) return;
    // Service Worker 정책상 https 또는 localhost 에서만 등록 가능
    const host = location.hostname;
    const isLocal = host === "localhost" || host === "127.0.0.1";
    if (location.protocol !== "https:" && !isLocal) return;
    window.addEventListener("load", () => {
      navigator.serviceWorker
        .register("/service-worker.js", { scope: "/" })
        .catch(() => {
          /* 조용히 실패 — 오프라인 캐시만 비활성, 앱 기능은 정상 동작 */
        });
    });
  };


  /* ==========================================================
     4. 초기화 및 내보내기
     ========================================================== */

  document.addEventListener("DOMContentLoaded", bindDeclarativeActions);
  registerServiceWorker();

  // 페이지 스크립트에서 FM.popup(), FM.toast(), FM.infiniteList() 등으로 접근
  return { popup, confirmNavigate, goBack, setPreview, formatPhone, toast, infiniteList };
})();
