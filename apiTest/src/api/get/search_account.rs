use serde_json::json;
use dotenvy::dotenv;
use std::{env, thread, time::Duration};

use crate::models::{info_user::InfoUser, login_response::{LoginResponse}};


pub async fn test_get_users_should_return_success() -> Result<(), Box<dyn std::error::Error>> {
    dotenv().ok();

    let api = env::var("API_URL").expect("API_URL não encontrada");
    let client = reqwest::Client::new();

    println!("🔑 Autenticando na API Java...");

    let email = env::var("USER_AUTH").expect("USER_AUTH não encontrado");
    let password = env::var("PASSWORD").expect("PASSWORD não encontrado");

    let login_response = client
        .post(format!("{}/email/login", api))
        .json(&json!({
            "email": email,
            "password": password
        }))
        .send()
        .await?;

    if login_response.status() != reqwest::StatusCode::OK {
        let err_text = login_response.text().await?;
        return Err(format!("Falha no login: {}", err_text).into());
    }

    let login_data: LoginResponse = login_response.json().await?;
    let token = login_data.token;

    println!("🚀 Buscando usuários com o token de autorização...");

    let response = client
        .get(format!("{}/user/all", api))
        .header("Authorization", format!("Bearer {}", token))
        .send()
        .await?;

    let status = response.status();
    if status != reqwest::StatusCode::OK {
        let error_body = response.text().await?;
        println!("❌ O Java retornou Status {}!", status);
        println!("Mensagem do erro do servidor: {}", error_body);
        
        return Err(format!("Server returned status {}", status).into());
    }

    let raw_json = response.text().await?;
    
    match serde_json::from_str::<Vec<InfoUser>>(&raw_json) {
        Ok(users) => {
            println!("✅ Usuários encontrados com sucesso: ");
            thread::sleep(Duration::from_millis(3000));
            for user in &users{
                match serde_json::to_string_pretty(user) {
                    Ok(vertical) =>{
                        print!("{}\n", vertical);
                        print!("--------------------------------------------------------------------------------------------------------------------\n")
                    }
                    Err(e) => {
                        eprintln!("❌ Erro ao formatar usuário para JSON: {}", e);
                    }
                }
            }
        },
        Err(e) => {
            eprintln!("\n❌ Erro de Desserialização no Rust: {}", e);
            return Err(e.into());
        }
    }

    Ok(())
}