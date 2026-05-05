console.log("Arquivos Mortos");
async function viewerEditProjeto() {
    console.log("Arquivo carregado!");
    console.log("Página carregada, buscando dados...");
    
    const inputId = document.querySelector('input[id="id"]') || document.querySelector('input[name="id"]');
    const projetoId = inputId ? inputId.value : null;
    if (!projetoId) {
        console.log("ID encontrado: " + projetoId);
        console.error("ID do projeto não encontrado no HTML. Verifique se o <input type='hidden' id='id'> tem valor.");
        return;
    }

    try {
        const response = await fetch(`/api/v1/projeto/buscar?id=${projetoId}`);
        if (!response.ok) throw new Error("Erro ao buscar projeto");
        
        const projeto = await response.json();

        document.getElementById("titulo").value = projeto.titulo || '';
        document.getElementById("descricao").value = projeto.descricao || '';
        document.getElementById("dataConclusao").value = projeto.dataConclusao || '';
        document.getElementById("status").value = projeto.status || 'SELECIONE_OPCAO';
        document.getElementById("tag-input").value = projeto.tecnologiasText || '';
        document.getElementById("isPublish").checked = projeto.isPublish === true;

        console.log("Campos preenchidos com sucesso!");
    } catch (error) {
        console.error("Erro ao carregar dados iniciais:", error);
    }
};
document.addEventListener("DOMContentLoaded", viewerEditProjeto);