async function consultarUsuario() {
    try{
        const response = await fetch("/api/v1/user/all");
        const usuarios = await response.json();

        const container = document.getElementById('container-usuarios');
        const semResultados = document.getElementById('sem-resultados');

        container.innerHTML = "";

        if(usuarios.length == 0){
            semResultados.classList.remove('hidden');
            return;
        }

        semResultados.classList.add('hidden');

        usuarios.forEach(user => {
            const fotoHtml = user.fotoPerfil 
            ? `<img src="${user.fotoPerfil}" class="rounded-lg" alt="Foto">`
                : `<i class="bi bi-person text-xl"></i>`;
            
            const card = `
                <div class="group bg-white border border-gray-100 rounded-2xl p-5 shadow-sm hover:shadow-xl transition-all duration-300 flex flex-col justify-between border-b-4 hover:border-b-purple-400">
                    <div>
                        <div class="flex items-start justify-between mb-4">
                            <div class="flex items-center gap-3">
                                <div class="w-10 h-10 rounded-full bg-gray-100 flex items-center justify-center text-gray-400 group-hover:bg-blue-50 group-hover:text-blue-500 transition-colors">
                                    ${fotoHtml}
                                </div>
                                <a href="/editUser/viewer/${user.id}" class="text-lg font-bold text-gray-800 hover:text-blue-600 transition-colors leading-tight">
                                    ${user.name}
                                </a>
                            </div>
                            <div class="relative">
                                <button class="p-1.5 rounded-lg hover:bg-gray-100 text-gray-400">
                                    <i class="bi bi-three-dots-vertical text-lg"></i>
                                </button>
                            </div>
                        </div>

                        <div class="mt-2">
                            <label class="text-[10px] uppercase tracking-widest font-bold text-gray-400 block mb-1">Descrição</label>
                            <div class="bg-gray-50 rounded-lg p-3 border border-gray-100">
                                <p class="text-sm text-gray-600 line-clamp-2">
                                    ${user.descricao || 'Sem descrição cadastrada.'}
                                </p>
                            </div>
                        </div>
                    </div>

                    <div class="mt-6 pt-4 border-t border-gray-50 flex justify-between items-center">
                         <span class="text-[10px] font-bold text-gray-300 uppercase tracking-tighter">ID: ${user.id}</span>
                         <a href="/panelUser/editar/${user.id}" class="text-xs font-bold text-purple-500 hover:text-blue-700 transition-colors uppercase">
                            Configurações →
                         </a>
                    </div>
                </div>
            `;
            container.innerHTML += card;
        });

    }catch(erro){
        console.error("Erro ao consumir a API: ", erro);
        alert("Não foi possível carregar os usuários")
    }
}

document.addEventListener('DOMContentLoaded', consultarUsuario);