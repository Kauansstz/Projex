function renderizarPanel(){
    const container = document.getElementById("containerCadastro");
    if (!container)return;

    container.innerHTML = `
                <div class="flex flex-col ">
                    <label class="font-sans text-[18px] w-[10rem]">Nome Completo</label>
                    <input 
                    placeholder="Digite o seu nome..." 
                    class="w-[22.5rem] p-[15px] rounded-[4px] border border-black outline-none focus:ring-1 focus:ring-black" 
                    type="text"  
                    id="name"
                    required />
                </div>
                <div class="flex flex-col">
                    <label class="font-sans text-[18px] w-[10rem]">E-mail</label>
                    <input 
                    type="email"
                    placeholder="exemplo@dominio.com" 
                    class="w-[22.5rem] p-[15px] rounded-[4px] border border-black outline-none focus:ring-1 focus:ring-black" 
                    required 
                    id="email"
                    autocomplete="email"
                    pattern="^[a-zA-Z0-9._%+-]+@dominio\.com$"
                    title="Digite um e-mail válido no formato: usuario@dominio.com"
                    />
                </div>
                <div class="flex flex-col">
                    <label class="font-sans text-[18px] w-[10rem]">Telefone</label>
                    <input 
                    type="tel" 
                    id="telefone"
                    placeholder="(11) 99999-9999" 
                    pattern="\(\d{2}\)\s?\d{4,5}-\d{4}"
                    maxlength="15"
                    class="w-[22.5rem] p-[15px] rounded-[4px] border border-black outline-none focus:ring-1 focus:ring-black" 
                    required />
                </div>
                <div class="flex flex-col">
                    <label class="font-sans text-[18px] w-[10rem]">CPF</label>
                    <input 
                    type="text" 
                    id="cpf"
                    placeholder="000.000.000-00" 
                    maxlength="14"
                    title="Digite apenas números"
                    class="w-[22.5rem] p-[15px] rounded-[4px] border border-black outline-none focus:ring-1 focus:ring-black" 
                    required
                     />
                </div>
                <div class="flex flex-col">
                    <label class="font-sans text-[18px] w-[20rem]">Data de Nascimento</label>
                    <input  
                    class="w-[22.5rem] p-[15px] rounded-[4px] cursor-pointer border border-black outline-none focus:ring-1 focus:ring-black" 
                    type="date" 
                    id="dataNasc"
                    required />

                </div>
                <div class="flex flex-col">
                    <label class="font-sans text-[18px]">Gênero</label>
                    <select id="genero" class="w-[22.5rem] p-[15px] rounded border border-black">
                        <option th:value="MASCULINO">Masculino</option>
                        <option th:value="FEMININO">Feminino</option>
                        <option th:value="OUTRO">Outro</option>
                    </select>
                </div>
                <div class="flex flex-col">
                    <label class="font-sans text-[18px] w-[10rem]">Senha</label>
                    <input 
                    type="password" 
                    id="senha"
                    placeholder="Digite a sua senha..." 
                    class="w-[22.5rem] p-[15px] rounded-[4px] border border-black outline-none focus:ring-1 focus:ring-black" 
                    autocomplete="new-password"
                    required 
                    />
                </div>
                <div class="flex flex-col">
                    <label class="font-sans text-[18px] w-[15rem]">Confirmação de senha</label>
                    <input 
                    type="password" 
                    id="confirmaSenha" 
                    placeholder="Confirme a sua senha" 
                    class="w-[22.5rem] p-[15px] rounded-[4px] border border-black outline-none focus:ring-1 focus:ring-black" 
                    autocomplete="new-password"
                    required />
                </div>
                <div class="flex flex-row w-[15rem] gap-[5px]">
                    <input 
                    type="checkbox" 
                    id="aceitarTermos" 
                    class="text-[15px] w-[11px] text-[#269fe6] accent-purple-600 cursor-pointer">
                    <span class="font-sans text-[13px] w-[15rem]">
                        Aceito os 
                        <a class="cursor-pointer text-[#269fe6] hover:text-[#2086c2]">termos</a> e 
                        <a class="cursor-pointer text-[#269fe6] hover:text-[#2086c2]">políticas</a>
                    </span>
                </div>
                <div class="flex flex-row w-[22.5rem] justify-between ">
                    <button type="button" onclick="cadastrarUser()" class="bg-blue-600 hover:bg-blue-700 text-white p-[8px] w-[5rem] rounded-[5px] shadow-lg hover:shadow-xl transition duration-300 flex items-center">Cadastrar</button>
                    <button type="button" onclick="(history.length>1 ? history.back() : window.location.href='/login')"  class="bg-purple-600 hover:bg-purple-700 text-white p-[8px] w-[5rem] rounded-[5px] shadow-lg hover:shadow-xl transition duration-300 flex flex-end">Cancelar</button>
                </div>
    `


}

async function cadastrarUser(){
    const name = document.getElementById("name").value;
    const email = document.getElementById("email").value;
    const password = document.getElementById("senha").value;
    const confirmPassword = document.getElementById("confirmaSenha").value;
    const dataNasc = document.getElementById("dataNasc").value;
    const cpf = document.getElementById("cpf").value;
    const genero = document.getElementById("genero").value;
    
    const userData= {
            name:name,
            email:email,
            password:password,
            confirmPassword:confirmPassword,
            dataNasc:dataNasc,
            genero:genero,
            cpf:cpf,
        };

    try{
    const response = await fetch('/api/v1/create/create', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body:JSON.stringify(userData)
        
    });
    if(response.ok){
        try{
            const user = await response.json();
            console.log("Criação do usuário criado com sucesso!")
            window.location.href = '/login';
        } catch(error){
            console.error("Erro ao transformar texto em JSON. O servidor mandou algo errado:", e);
            alert("Erro no formato de dados do servidor.");
        }
    } else if(response.status === 409){
        alert("Usuário já existe com esse email");
    } else {
        const errorMsg = await response.text();
        alert("Erro do servidor: " + errorMsg);
    }
        
    } catch(error){
        console.error("Erro na conexão: ", error);
        alert("Não foi possível conectar ao servidor. Verifique sua internet.");
    }
}

document.addEventListener("DOMContentLoaded", renderizarPanel);