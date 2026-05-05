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

    try {
        const response = await fetch('/api/v1/projeto/editProjeto', {
            method: 'POST',
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