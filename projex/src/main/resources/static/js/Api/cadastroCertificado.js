async function criarProjeto() {
    const formData = new FormData();
        const fileField = document.getElementById("fileInput");
    
    if (fileField.files.length > 0) {
        formData.append("anexo", fileField.files[0]);
    }

    formData.append("titulo", document.getElementById("titulo").value);
    formData.append("descricao", document.getElementById("descricao").value);
    formData.append("instituicao", document.getElementById("instituicaoInput").value);
    formData.append("typeCertificate", document.getElementById("TypeInput").value);
    formData.append("status", document.getElementById("status").value);
    formData.append("isPublish", document.getElementById("isPublish").checked);
    formData.append("category", document.getElementById("categoria").value);
    formData.append("dataConclusao", document.getElementById("dataConclusao").value);

    for (let pair of formData.entries()) {
        console.log(pair[0] + ': ' + pair[1]);

    }

    try{
        console.log("Try");
        const response = await fetch('api/v1/criarProjeto',{
            method: 'POST',
            body: formData
        }).then(
            async response  => {
                const msg = await response.text();
                console.log(msg);
                console.log("Teste")
                if(!response.ok){
                    window.__showToast(msg, "error");
                }else{
                    window.__showToast("Certificado criado com sucesso!", "success")
                    setTimeout(() => window.location.href= "/panelCertificados", 3000);
                }
            }
       )
} catch(error){
        console.log("Erro na requisição: " + error);
    }
}