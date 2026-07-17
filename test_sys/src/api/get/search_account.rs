use dotenvy::dotenv;
use std::{env, thread, time::Duration};
use crate::models::info_user::InfoUser;
use crate::utils::{token::token, loading::loading};


pub async fn test_get_users_should_return_success() -> Result<(), Box<dyn std::error::Error>> {
    dotenv().ok();

    println!("{:-<60}", "");
    println!("|           INICIANDO O TESTE DE PESQUISA DE USUARIO       |");
    println!("{:-<60}", "");

    let api = env::var("API_URL").expect("API_URL não encontrada");
    let client = reqwest::Client::new();

    println!("🔑 Autenticando na API Java...");

    let token = token().await.unwrap();
    thread::sleep(Duration::from_secs(1));
    println!("🚀 Buscando usuários com o token de autorização...");

    let response = client
        .get(format!("{}/user/all", api))
        .bearer_auth(token)
        .send()
        .await
        .unwrap();

    loading();
    
    let status = response.status();
    if status != reqwest::StatusCode::OK {
        let error_body = response.text().await?;
        println!("❌ O Java retornou Status {}!", status);
        println!("Mensagem do erro do servidor: {}", error_body);
        
        return Err(format!("Server returned status {}", status).into());
    }
    thread::sleep(Duration::from_secs(1));
    let raw_json = response.text().await?;

    match serde_json::from_str::<Vec<InfoUser>>(&raw_json) {
        Ok(users) => {
            println!("Status: {}", status);
            thread::sleep(Duration::from_secs(1));
            println!("Quantidade de usuário cadastrados: {}", users.len());
            println!("{:-<60}", "");
        },
        Err(e) => {
            eprintln!("\n❌ Erro de Desserialização no Rust: {}", e);
            return Err(e.into());
        }
    }

    Ok(())
}