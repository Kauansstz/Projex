async function criarProjeto() {
    const titulo = document.getElementById("titulo");
    const descricao = document.getElementById("descricao");
    const status = document.getElementById("status");
    const dataConclusao = document.getElementById("dataConclusao");
    const tecnologiasText = document.getElementById("tag-input");
    const isPublish = document.getElementById("isPublish");
    
    const dados = {
        titulo: titulo.value,
        descricao: descricao.value,
        status: status.value,
        dataConclusao: dataConclusao.value,
        tecnologiasText: tecnologiasText.value,
        isPublish: isPublish.value,
    };
    try{
        const response = await fetch('api/v1/create/projeto', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(dados)
        }).then(async response => {
    const msg = await response.text(); // Pega "O título é obrigatório"

    if (!response.ok) {
        // MOSTRAR ERRO NO TOPO
        const container = document.getElementById('container-notificacao');
        const alertaErro = document.getElementById('alerta-erro');
        const textoErro = document.getElementById('texto-erro');

        textoErro.innerText = msg;
        container.classList.remove('hidden');
        alertaErro.classList.remove('hidden');

        // Esconder após 5 segundos
        setTimeout(() => {
            container.classList.add('hidden');
        }, 5000);

    } else {
        // Lógica de sucesso (ex: redirecionar ou mostrar alerta verde)
        window.location.href = "/dashboard"; 
    }
});

        if (response.ok){
            try{
                console.log("Criação do projeto realizado com sucesso!");
                window.location.href = "/panelProjetos";
            } catch (error){
                console.log("Houve um erro inesperado.");
            }
        }
    } catch(error){
        console.log("Erro na requisição:", error);
    }
}