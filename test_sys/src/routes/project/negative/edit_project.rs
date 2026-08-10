use std::time::Instant;
use std::env;
use dotenvy::dotenv;
use reqwest::{Client, StatusCode, redirect::Policy};
use crate::{common::{login::login, token::token}, models::info_project::InfoProject};

pub async fn test_route_edit_project_should_return_302() -> Result<(), Box<dyn std::error::Error>>{
    dotenv().ok();
    let inicio = Instant::now();
    let api = env::var("API_URL").expect("API_URL não foi encontrada");
    let client = login().await?;

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
        println!("Mensagem do erro do servidor na hora da criacao: {}", raw_json);
    }

    let projeto_criado: InfoProject = match serde_json::from_str(&raw_json) {
        Ok(projetos)=>{
            projetos
        }
        Err(e) =>{
            eprintln!("❌ Erro de Desserialização do Rust: {}", e);
            eprintln!("Json bruto recebido1: {}", raw_json);
            return  Err(e.into());
        }     
    };

    let client = Client::builder()
    .redirect(Policy::none())
    .build()?;
    let projeto_id = &projeto_criado.id.expect("Java deveria ter retornado o ID do projeto");
    let route = client.get(format!("http://localhost:8080/panelProjetos/editar/{}", projeto_id)).send().await?;
    let status_route = route.status();
    
    let _responde_delete = client
    .delete(format!("{}/delete/projeto/{}", api, projeto_id))
    .bearer_auth(token)
    .send()
    .await?;

    let duracao_projeto = inicio.elapsed();
    
    if status_route == StatusCode::OK {
        return Err(format!(
        "Status: {} | Rota de edicao de projeto [FALIED]........ Latencia: {:.2?}", status_route,  duracao_projeto
    ).into());

    }else {
        print!("Status: {}", status_route);
        println!(" | Rota de edicao de projeto [OK]..... Latencia: {:.2?}", duracao_projeto);
    }

    Ok(())
}