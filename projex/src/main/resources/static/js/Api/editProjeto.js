async function editProjeto() {
    const titulo = document.getElementById("titulo").value;
    const descricao = document.getElementById("descricao").value;
    const dataConclusao = document.getElementById("dataConclusao").value;
    const status = document.getElementById("status").value;
    const tecnologiasText = document.getElementById("tag-input").value;
    const isPublish = document.getElementById("isPublish").value;

    const data = {
        titulo: titulo,
        descricao: descricao,
        dataConclusao: dataConclusao,
        status: status,
        tecnologiasText: tecnologiasText,
        isPublish: isPublish,
    };
    try{

        const response = await fetch('api/v1/projeto/editProjeto', {
            method: 'POST',
            heards: {'Content-Type': 'application/json'},
            body: JSON.stringify(data)
        });

        if(!response.ok){
            console.log("Houve algum erro no processo de salvamento")
        } 
        window.location.href= '/panelProjetos';
        
    }catch(erro){
        console.log("Erro na conexão: ", error);
    }
    
}