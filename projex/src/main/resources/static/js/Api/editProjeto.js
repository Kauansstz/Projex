async function editProjeto() {
    console.log("Iniciando salvamento...");

    const data = {
        titulo: document.getElementById("titulo").value,
        descricao: document.getElementById("descricao").value,
        dataConclusao: document.getElementById("dataConclusao").value,
        status: document.getElementById("status").value,
        tecnologiasText: document.getElementById("tag-input").value,
        isPublish: document.getElementById("isPublish").checked
    };
    
    const inputId = document.querySelector('input[id="id"]') || document.querySelector('input[name="id"]');
    const projetoId = inputId ? inputId.value : null;
    if (!projetoId) {
        console.log("ID encontrado: " + projetoId);
        console.error("ID do projeto não encontrado no HTML. Verifique se o <input type='hidden' id='id'> tem valor.");
        return;
    }
    console.log("Teste")
    try {
        console.log("Teste 2")
        const response = await fetch(`/api/v1/projeto/editProjeto?id=${projetoId}`, {
            method: 'PATCH',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(data)
        });

        if (response.ok) {
            console.log("Projeto salvo!");
            window.location.href = '/panelProjetos';
        } else {
            console.error("Erro no servidor ao salvar.");
        }
    } catch (error) {
        console.error("Erro na conexão: ", error);
    }
}