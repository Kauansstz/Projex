// /js/config.js
document.addEventListener("DOMContentLoaded", () => {
  const tabs = document.querySelectorAll(".tab-link");
  const contents = document.querySelectorAll(".tab-content");
  if (tabs.length === 0) return;
  tabs.forEach(tab => {
    tab.addEventListener("click", () => {
      tabs.forEach(t => t.classList.remove("border-blue-600", "text-blue-600"));
      tab.classList.add("border-blue-600", "text-blue-600");
      contents.forEach(c => c.classList.add("hidden"));
      const target = document.getElementById(tab.dataset.tab);
      if (target) target.classList.remove("hidden");
    });
  });
  if (tabs[0]) tabs[0].click();
});

// tenta pegar CSRF do meta (para Spring Security)
function getCsrf() {
  const tokenMeta = document.querySelector('meta[name="_csrf"]');
  const headerMeta = document.querySelector('meta[name="_csrf_header"]');
  return {
    token: tokenMeta ? tokenMeta.getAttribute("content") : null,
    header: headerMeta ? headerMeta.getAttribute("content") : "X-CSRF-TOKEN"
  };
}

async function salvarConfiguracoes() {
    const configuracoes = {
        tema: document.getElementById('tema').value,
        notificacoes: document.getElementById('notificacoes').checked,
        idioma: document.getElementById('idioma').value
    };

    try {
        const response = await fetch('/api/configuracoes', {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(configuracoes)
        });

        console.log("Status:", response.status);

        const respostaServidor = await response.text();
        console.log("Resposta do servidor:", respostaServidor);

        if (!response.ok) {
            throw new Error("Erro ao salvar configurações: " + respostaServidor);
        }

        alert("✅ Configurações salvas com sucesso!");
    } catch (error) {
        console.error(error);
        alert("❌ Falha ao salvar configurações.");
    }
}


async function atualizarSenha() {
  const senha = document.getElementById("senha")?.value;
  const confirmar = document.getElementById("confirmarSenha")?.value;
  if (!senha || senha !== confirmar) return alert("As senhas não coincidem!");

  const csrf = getCsrf();
  const headers = { "Content-Type": "application/json" };
  if (csrf.token) headers[csrf.header] = csrf.token;

  try {
    const res = await fetch("/api/configuracoes/senha", {
      method: "PUT",
      headers,
      body: JSON.stringify({ senha })
    });
    console.log("Status senha:", res.status, await res.text());
    if (res.ok) alert("Senha atualizada!");
    else alert("Erro ao atualizar senha: " + res.status);
  } catch (err) {
    console.error("Erro fetch (senha):", err);
    alert("Erro de rede ao atualizar senha.");
  }
}
