// 📜 alerts.js — versão estável com fade garantido e limite de toasts
document.addEventListener("DOMContentLoaded", () => {
  const alertDuration = 4000; // tempo visível (ms)
  const fadeDuration = 500;   // duração do fade (ms)
  const maxToasts = 5;        // limite máximo de toasts simultâneos
  const debug = false;        // true para logs no console

  // --- Injeta estilos (independente de framework ou CSS externo)
  (function injectStyles() {
    const css = `
      #toast-container {
        position: fixed;
        top: 1rem;
        right: 1rem;
        z-index: 9999;
        display: flex;
        flex-direction: column;
        gap: 10px;
        pointer-events: none;
      }
      .toast {
        pointer-events: auto;
        padding: 1rem 1.5rem;
        border-radius: 8px;
        color: #fff;
        font-weight: 700;
        box-shadow: 0 3px 8px rgba(0,0,0,0.2);
        opacity: 0;
        transform: translateY(16px);
        transition: opacity ${fadeDuration}ms ease, transform ${fadeDuration}ms ease;
        will-change: opacity, transform;
      }
      .toast.show {
        opacity: 1;
        transform: translateY(0);
      }
    `;
    const style = document.createElement("style");
    style.setAttribute("data-from", "alerts.js");
    style.appendChild(document.createTextNode(css));
    document.head.appendChild(style);
  })();

  // --- Garante que o container exista
  let container = document.getElementById("toast-container");
  if (!container) {
    container = document.createElement("div");
    container.id = "toast-container";
    document.body.appendChild(container);
  }

  // --- Cria o elemento do toast
  function createToastElement(message, type) {
    const toast = document.createElement("div");
    toast.className = "toast";
    toast.setAttribute("role", "status");
    toast.setAttribute("aria-live", "polite");
    toast.textContent = message ?? "";

    // Cor inline por tipo (evita problemas de CSS externo)
    if (type === "success") toast.style.backgroundColor = "#28a745";
    else if (type === "error") toast.style.backgroundColor = "#dc3545";
    else toast.style.backgroundColor = "#17a2b8";

    return toast;
  }

  // --- Exibe o toast na tela
  function showToast(message, type = "info") {
    if (!message) {
      if (debug) console.log("[toast] mensagem vazia, ignorando");
      return;
    }

    // Remove o mais antigo se exceder o limite
    if (container.children.length >= maxToasts) {
      container.firstChild.remove();
      if (debug) console.log("[toast] limite excedido, removendo o mais antigo");
    }

    const toast = createToastElement(message, type);
    container.appendChild(toast);

    // Força reflow para permitir transição
    void toast.offsetWidth;
    requestAnimationFrame(() => toast.classList.add("show"));

    if (debug) console.log("[toast] mostrado:", message);

    // Após o tempo de exibição, inicia o fade-out
    const hideTimeout = setTimeout(() => {
      if (debug) console.log("[toast] iniciando fade-out:", message);

      // Força reflow ANTES de remover a classe (garante transição)
      void toast.offsetWidth;
      toast.classList.remove("show");

      // Fallback caso transitionend não seja disparado
      const fallback = setTimeout(() => {
        if (toast.parentElement) toast.remove();
        if (debug) console.log("[toast] fallback remove:", message);
      }, fadeDuration + 50);

      // Remove quando o fade terminar
      toast.addEventListener("transitionend", (ev) => {
        if (ev.propertyName !== "opacity") return;
        clearTimeout(fallback);
        if (toast.parentElement) toast.remove();
        if (debug) console.log("[toast] transitionend remove:", message);
      });
    }, alertDuration);

    // Retorna função para cancelar manualmente
    return () => {
      clearTimeout(hideTimeout);
      void toast.offsetWidth;
      toast.classList.remove("show");
      setTimeout(() => toast.remove(), fadeDuration + 20);
    };
  }

  // --- Lê mensagens automáticas do DOM
  try {
    const successEl = document.getElementById("alert-sucesso");
    const errorEl = document.getElementById("alert");
    const successMessage = successEl ? successEl.textContent.trim() : "";
    const errorMessage = errorEl ? errorEl.textContent.trim() : "";


    if (successMessage) showToast(successMessage, "success");
    if (errorMessage) showToast(errorMessage, "error");
  } catch (e) {
    if (debug) console.error("[toast] erro ao ler mensagens do DOM", e);
  }

  // --- Remove parâmetros de URL irrelevantes (ex: ?error, ?logout)
  if (window.location.search.includes("error") || window.location.search.includes("logout")) {
    const cleanUrl = window.location.pathname;
    window.history.replaceState(null, "", cleanUrl);
  }

  // --- Expor manualmente via console: window.__showToast("Olá", "success")
  window.__showToast = showToast;
});
