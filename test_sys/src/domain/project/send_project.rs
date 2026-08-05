use std::{env, thread, time::Duration};
use dotenvy::dotenv;
use reqwest::StatusCode;
use std::time::Instant;
use crate::{models::info_project::InfoProject, common::{login::login, token::token}};

pub async fn test_post_project_should_return_success() -> Result<(), Box<dyn ::std::error::Error>>{
    dotenv().ok();
    let inicio = Instant::now();
    let api = env::var("API_URL").expect("API_URL não foi encontrada");
    let client = login().await?;

    thread::sleep(Duration::from_secs(1));
    let dados = serde_json::json!({
        "titulo": "Teste de criação",
        "descricao": "Teste de descriação",
        "isPublish": true,
        "tecnologiasText": vec!["Python;Java;"],
        "tecnologias": vec!["Python;Java;"],
        "dataConclusao": "2026-07-14",
        "status": "CONCLUIDO",
        "criado_em": "2026-07-14",
        "atualizadoEm": "2026-07-14",
        "dono": 2
    });

    let token = token().await.unwrap();
    let response = client
    .post(format!("{}/create/projeto", api))
    .bearer_auth(token.clone())
    .json(&dados)
    .send()
    .await?;

    let status = response.status();
    let raw_json = response.text().await?;
    if status != StatusCode::CREATED && status != StatusCode::OK {
        println!("Mensagem do erro do servidor: {}", raw_json);
    }

    let projeto_criado: InfoProject = match serde_json::from_str(&raw_json) {
        Ok(projetos)=>{
            projetos
        }
        Err(e) =>{
            eprintln!("❌ Erro de Desserialização do Rust: {}", e);
            eprintln!("Json bruto recebido: {}", raw_json);
            return  Err(e.into());
        }     
    };

    thread::sleep(Duration::from_secs(1));

    let projeto_id = &projeto_criado.id.expect("Java deveria ter retornado o ID do projeto");
    
    let responde_delete = client
    .delete(format!("{}/delete/projeto/{}", api, projeto_id))
    .bearer_auth(token)
    .send()
    .await?;
    
    let status_delete = responde_delete.status();
    if !status_delete.is_success(){
        return  Err(format!("Server returned status {}", status_delete ).into());
    }

    print!("Status: {}", status);
    let duracao_projeto = inicio.elapsed();
    println!(" | Criar e deletar projeto [OK].......... Latencia: {:.2?}", duracao_projeto);
    Ok(())
}