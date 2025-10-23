// 📜 alerts.js
document.addEventListener("DOMContentLoaded", () => {
  // 🕒 tempo em milissegundos (3000 = 3 segundos)
  const alertDuration = 3000;

  // Seleciona os elementos que possuem th:if (Thymeleaf renderiza no HTML)
  const alerts = document.querySelectorAll('.alert');

  alerts.forEach(alert => {
    // Só executa se o alerta estiver visível
    if (alert.offsetParent !== null) {
      setTimeout(() => {
        alert.style.transition = "opacity 0.5s ease";
        alert.style.opacity = "0";
        // Remove depois da animação
        setTimeout(() => alert.remove(), 500);
      }, alertDuration);
    }
  });
  if (window.location.search.includes("error") || window.location.search.includes("logout")) {
    const cleanUrl = window.location.pathname;
    window.history.replaceState(null, "", cleanUrl);
  }
});
