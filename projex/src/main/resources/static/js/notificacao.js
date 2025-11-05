// 📜 alerts.js — versão com notificações flutuantes
document.addEventListener("DOMContentLoaded", () => {
  const alertDuration = 4000; // (4 segundos)

  let container = document.getElementById("toast-container");
  if (!container) {
    container = document.createElement("div");
    container.id = "toast-container";
    container.style.position = "fixed";
    container.style.top = "1rem";
    container.style.right = "1rem";
    container.style.zIndex = "9999";
    document.body.appendChild(container);
  }

  function showToast(message, type = "info") {
    const toast = document.createElement("div");
    toast.textContent = message;
    toast.style.padding = "1rem 1.5rem";
    toast.style.marginTop = "10px";
    toast.style.borderRadius = "8px";
    toast.style.color = "white";
    toast.style.fontWeight = "bold";
    toast.style.boxShadow = "0 3px 8px rgba(0,0,0,0.2)";
    toast.style.opacity = "0";
    toast.style.transition = "opacity 0.5s ease";

    if (type === "success") toast.style.backgroundColor = "#28a745"; 
    else if (type === "error") toast.style.backgroundColor = "#dc3545"; 
    else toast.style.backgroundColor = "#17a2b8"; 

    container.appendChild(toast);
    setTimeout(() => (toast.style.opacity = "1"), 100);

    setTimeout(() => {
      toast.style.opacity = "0";
      setTimeout(() => toast.remove(), 500);
    }, alertDuration);
  }

  const successMessage = document.getElementById("mensagemSucesso")?.value;
  const errorMessage = document.getElementById("mensagemErro")?.value;

  if (successMessage) showToast(successMessage, "success");
  if (errorMessage) showToast(errorMessage, "error");

  if (window.location.search.includes("error") || window.location.search.includes("logout")) {
    const cleanUrl = window.location.pathname;
    window.history.replaceState(null, "", cleanUrl);
  }
});
