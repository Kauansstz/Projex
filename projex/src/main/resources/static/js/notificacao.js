document.addEventListener("DOMContentLoaded", () => {
    const alertDuration = 5000;
    const debug = true;

    const container = document.getElementById('container-notificacao');
    const alertaErro = document.getElementById('alerta-erro');
    const textoErro = document.getElementById('texto-erro');
    const alertaSucesso = document.getElementById('alerta-sucesso');
    const textoSucesso = document.getElementById('texto-sucesso');

    function showNotification(message, type = "success") {
        if (!message || !container) return;

        if (debug) console.log(`[Notificação] exibindo ${type}: ${message}`);
        container.classList.remove('hidden');
        alertaErro.classList.add('hidden');
        alertaSucesso.classList.add('hidden');

        if (type === "error" || type === "danger") {
            textoErro.innerText = message;
            alertaErro.classList.remove('hidden');
        } else {
            textoSucesso.innerText = message;
            alertaSucesso.classList.remove('hidden');
        }

        setTimeout(() => {
            container.classList.add('hidden');
            if (debug) console.log("[Notificação] ocultada automaticamente");
        }, alertDuration);
    }

    window.__showToast = showNotification;

    const flashError = document.getElementById("alert")?.textContent.trim();
    const flashSuccess = document.getElementById("alert-sucesso")?.textContent.trim();

    if (flashError) showNotification(flashError, "error");
    if (flashSuccess) showNotification(flashSuccess, "success");

    if (window.location.search.includes("error") || window.location.search.includes("sucesso")) {
        window.history.replaceState(null, "", window.location.pathname);
    }
});