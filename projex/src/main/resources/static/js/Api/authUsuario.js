function renderizarTelaLogin() {
    const container = document.getElementById("container-login");
    if (!container) return;

    container.innerHTML = `
        <div class="flex flex-col">
                    <label class="font-sans text-[21px] w-[5rem]">E-mail</label>
                    <input 
                    class="w-[22.5rem] p-[15px] rounded-[4px] border border-black outline-none focus:ring-1 focus:ring-black" 
                    type="email" 
                    id="email" required />
                </div>
                <div class="flex flex-col">
                    <label class="font-sans text-[21px]">Senha</label>
                    <input 
                    class="w-[22.5rem] p-[15px] rounded-[4px] border border-black outline-none focus:ring-1 focus:ring-black" 
                    type="password" id="senha" 
                    required />
                </div>
                <div class="w-12">
                    <button type="button" class="w-[5rem] bg-blue-500 p-[8px] text-white rounded-[5px]"
                    onclick="authUsuario()">Entrar</button>
                </div>`;
}

async function authUsuario() {
    const email = document.getElementById("email").value;
    const senha = document.getElementById("senha").value;

    try {
        const response = await fetch("/api/v1/email/login", {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ email: email, password: senha })
        });


        if (response.ok) {
            const text = await response.text(); 
            console.log("Texto bruto recebido do servidor:", text);

            try {
                const user = JSON.parse(text);
                localStorage.setItem('usuarioLogado', user.nome); 
                console.log("Login OK, redirecionando...");
                window.location.href = '/home';
            } catch (e) {
                console.error("Erro ao transformar texto em JSON. O servidor mandou algo errado:", e);
                alert("Erro no formato de dados do servidor.");
            }
        } else if (response.status === 401) {
            alert("E-mail ou senha incorretos!");
        } else if (response.status === 403) {
            alert("Sua conta está desativada. Entre em contato com o suporte.");
        } else {
            alert("Erro inesperado no servidor.");
        }
        
    } catch (error) {
        console.error("Erro na conexão:", error);
        alert("Não foi possível conectar ao servidor. Verifique sua internet.");
    }
}
document.addEventListener('DOMContentLoaded', renderizarTelaLogin);