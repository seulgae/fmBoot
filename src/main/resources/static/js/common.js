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

  const bindDeclarativeActions = () => {
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

  document.addEventListener("DOMContentLoaded", bindDeclarativeActions);

  return { popup, confirmNavigate, goBack, setPreview, formatPhone };
})();
