use std::{env, thread, time::Duration};
use dotenvy::dotenv;
use reqwest::StatusCode;

use crate::{models::info_project::InfoProject, utils::{loading::loading, login::login, token::token}};

pub async fn test_post_project_should_return_success() -> Result<(), Box<dyn ::std::error::Error>>{
    dotenv().ok();
    println!("{:-<60}", "");
    println!("|           INICIANDO O TESTE DE CRIACAO DE PROJETO        |");
    println!("{:-<60}", "");

    let api = env::var("API_URL").expect("API_URL não foi encontrada");
    let client = login().await?;

    println!("⚙ Verificando os valores");
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


    println!("🔑 Autenicando na API do Java");
    thread::sleep(Duration::from_secs(1));

    let token = token().await.unwrap();
    let response = client
    .post(format!("{}/create/projeto", api))
    .bearer_auth(token.clone())
    .json(&dados)
    .send()
    .await?;
    loading();

    let status = response.status();
    println!("Verificando o status da API: {}", status);
    let raw_json = response.text().await?;
    if status != StatusCode::CREATED && status != StatusCode::OK {
        println!("Mensagem do erro do servidor: {}", raw_json);
    }

    let projeto_criado: InfoProject = match serde_json::from_str(&raw_json) {
        Ok(projetos)=>{
            eprint!("✅ API de criação de projeto respondeu com sucesso!");
            projetos
        }
        Err(e) =>{
            eprintln!("❌ Erro de Desserialização do Rust: {}", e);
            eprintln!("Json bruto recebido: {}", raw_json);
            return  Err(e.into());
        }     
    };

    thread::sleep(Duration::from_secs(1));
    eprintln!("✅ Projeto cadastrado temporariamente com o ID: {:?}", projeto_criado.id);
    println!("");

    let projeto_id = &projeto_criado.id.expect("Java deveria ter retornado o ID do projeto");

    println!("{:-<60}", "");
    println!("|              🧹 Limpando o banco de dados...              |");
    println!("{:-<60}", "");
    thread::sleep(Duration::from_secs(1));
    

    eprintln!("Projeto que está sendo deletado pelo ID: {:?}", projeto_id);

    let responde_delete = client
    .delete(format!("{}/delete/projeto/{}", api, projeto_id))
    .bearer_auth(token)
    .send()
    .await?;
    loading();
    
    let status_delete = responde_delete.status();
    let raw_json = responde_delete.text().await?;
    if !status_delete.is_success(){
        println!("❌ O Java retornou Status {}!", status_delete);
        println!("Mensagem do erro do servidor: {}", raw_json);
        return  Err(format!("Server returned status {}", status_delete ).into());
    }

    eprintln!("✅ Limpeza do banco de dados realizada com sucesso!");
    eprintln!("Status do Java: {}", status_delete);

    Ok(())
}