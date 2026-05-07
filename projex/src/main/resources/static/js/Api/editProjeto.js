async function editProjeto() {
    console.log("Iniciando salvamento...");

    const data = {
        titulo: document.getElementById("titulo").value,
        descricao: document.getElementById("descricao").value,
        dataConclusao: document.getElementById("dataConclusao").value,
        status: document.getElementById("status").value,
        tecnologiasText: document.getElementById("tecnologiasText").value,
        isPublish: document.getElementById("isPublish").checked
    };
    
    const inputId = document.querySelector('input[id="id"]') || document.querySelector('input[name="id"]');
    const projetoId = inputId ? inputId.value : null;
    if (!projetoId) {
        console.log("ID encontrado: " + projetoId);
        console.error("ID do projeto não encontrado no HTML. Verifique se o <input type='hidden' id='id'> tem valor.");
        return;
    }
  
    try {
        const response = await fetch(`/api/v1/projeto/editProjeto?id=${projetoId}`, {
            method: 'PATCH',
            credentials: 'include',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(data)
        }).then(async response => {
            const msg = await response.text();

            if (!response.ok) {
                window.__showToast(msg, "error");
            } else {
                window.__showToast("Projeto criado com sucesso!", "success");
                setTimeout(() => window.location.reload(), 1000);
            }
        });

        if (response.ok) {
            try{
                console.log("Projeto salvo!");
                window.location.href = '/panelProjetos';
            }catch(error){
                console.error("Erro de salvar os dados", e);
            }
        } else {
            console.error("Erro no servidor ao salvar.");
        }
    } catch (error) {
        console.error("Erro na conexão: ", error);
    }
}