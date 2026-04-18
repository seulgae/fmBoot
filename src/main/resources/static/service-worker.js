/*
  FM Manager / Service Worker
  - 정적 자원: cache-first (만료 시 백그라운드 업데이트)
  - HTML 문서: network-first (실패 시 캐시 → 오프라인 페이지)
  - 비-GET / 로그인·결제 엔드포인트: 캐시 안 함 (항상 네트워크)
*/
const VERSION = "fm-v1";
const STATIC_CACHE = `${VERSION}-static`;
const RUNTIME_CACHE = `${VERSION}-runtime`;

const PRECACHE_URLS = [
  "/",
  "/offline.html",
  "/css/app.css",
  "/js/common.js",
  "/icons/icon.svg",
  "/manifest.webmanifest"
];

// 캐시에 담지 않을 경로(민감/동적)
const NO_CACHE_PATTERNS = [
  /^\/login\//,
  /^\/payment\/(kakaoPay|pay_reservation)/,
  /^\/h2-console/
];

const shouldBypass = (url) => NO_CACHE_PATTERNS.some((re) => re.test(url.pathname));

self.addEventListener("install", (event) => {
  event.waitUntil(
    caches.open(STATIC_CACHE).then((cache) => cache.addAll(PRECACHE_URLS)).then(() => self.skipWaiting())
  );
});

self.addEventListener("activate", (event) => {
  event.waitUntil(
    caches.keys()
      .then((keys) => Promise.all(keys.filter((k) => !k.startsWith(VERSION)).map((k) => caches.delete(k))))
      .then(() => self.clients.claim())
  );
});

self.addEventListener("fetch", (event) => {
  const { request } = event;
  if (request.method !== "GET") return;

  const url = new URL(request.url);
  if (url.origin !== self.location.origin) return;
  if (shouldBypass(url)) return;

  const isHTML = request.mode === "navigate" || (request.headers.get("accept") || "").includes("text/html");

  if (isHTML) {
    event.respondWith(
      fetch(request)
        .then((response) => {
          const copy = response.clone();
          caches.open(RUNTIME_CACHE).then((cache) => cache.put(request, copy));
          return response;
        })
        .catch(() => caches.match(request).then((hit) => hit || caches.match("/offline.html")))
    );
    return;
  }

  // 정적 자원: cache-first + 백그라운드 갱신
  event.respondWith(
    caches.match(request).then((cached) => {
      const fetchPromise = fetch(request)
        .then((response) => {
          if (response && response.status === 200 && response.type === "basic") {
            const copy = response.clone();
            caches.open(RUNTIME_CACHE).then((cache) => cache.put(request, copy));
          }
          return response;
        })
        .catch(() => cached);
      return cached || fetchPromise;
    })
  );
});
