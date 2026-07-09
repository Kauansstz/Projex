use chrono::format::Numeric::Timestamp;
use serde_json::json;
use dotenvy::dotenv;
use std::thread;
use std::time::Duration;
use std::time::{SystemTime, UNIX_EPOCH};
use std::{collections::HashMap, env};

use crate::models::{info_user::{self, InfoUser}, login_response::LoginResponse};


pub async fn test_post_users_should_return_success() -> Result<(), Box<dyn std::error::Error>> {
    dotenv().ok();

    println!("{:-<60}", "");
    println!("|           INICIANDO O TESTE DE CRIACAO DE USUARIO        |");
    println!("{:-<60}", "");

    let API = env::var("API_URL").expect("API_URL não encontrada");
    let client = reqwest::Client::new();

    println!("🔑 Autenticando na API Java...");
    thread::sleep(Duration::from_millis(3000));

    let email = env::var("USER_AUTH").expect("USER_AUTH não encontrado");
    let password = env::var("PASSWORD").expect("PASSWORD não encontrado");

    let login_response = client
        .post(format!("{}/email/login", API))
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
    let timestemp = SystemTime::now()
    .duration_since(UNIX_EPOCH)
    .unwrap()
    .as_secs()
    .to_string();

    let cpf_dinamico = format!("999{}", &timestemp[0..8]);
    let email_dinamico = format!("teste.teste.{}@gmail.com", timestemp);

    println!("🚀 Buscando usuários com o token de autorização...");
    println!("");
    thread::sleep(Duration::from_millis(3000));
    let dados = serde_json::json!({
    "name": "Teste",
    "email": email_dinamico,
    "password": "Test12345",
    "confirmPassword": "Test12345",
    "cpf": cpf_dinamico,
    "role": "ROLE_DEFAULT",
    "dataNasc": "2003-12-20",
    "genero": "MASCULINO",
    "aceitarTermos": true,
    "tentativasLogin": 0,
    "descricao": "Usuário criado via teste automatizado em Rust."
    });

    let response = client
        .post(format!("{}/create/usuario", API))
        .header("Authorization", format!("Bearer {}", token))
        .json(&dados)
        .send()
        .await?;

    

    
    let status = response.status();
    let raw_json = response.text().await?;

    if status != reqwest::StatusCode::OK && status != reqwest::StatusCode::CREATED{
        println!("❌ O Java retornou Status {}!", status);
        println!("Mensagem do erro do servidor: {}", raw_json);
        
        return Err(format!("Server returned status {}", status).into());
    }

    
    let usuario_criado:InfoUser= match serde_json::from_str::<InfoUser>(&raw_json){
        Ok(user)=> {
            println!("✅ API de criação de usuário respondeu com sucesso!");
            user
        }
        Err(e)=> {
            eprintln!("\n❌ Erro de Desserialização no Rust (Esperava objeto único): {}", e);
            println!("JSON bruto recebido: {}", raw_json);
            return Err(e.into());
        }
    };

    println!("✅ Usuário cadastrado temporariamente com o ID: {:?}", usuario_criado.id);
    println!("");
    thread::sleep(Duration::from_millis(3000));
    
    assert!(status.is_success(), "A API Java não retornou um status de sucesso!");
    let usuario_id = usuario_criado.id.expect("O Java deveria ter retornado o ID do usuário.");

    println!("🧹 Limpando o banco de dados...");
    println!("");
    thread::sleep(Duration::from_millis(3000));

    let response_delete = client
    .delete(format!("{}/delete/user/{}", API, usuario_id))
    .header("Authorization", format!("Bearer {}", token))
    .send()
    .await?;
    
    let status_delete = response_delete.status();
    let reqtest_delete = response_delete.text().await?;

    if status_delete.is_success() {
        println!("✅ Limpeza do banco de dados realizada com sucesso!");
        println!("Status do Java: {}", status_delete);
        println!("");
    } else {
        eprintln!("❌ Falha ao limpar o banco. Resposta do servidor: {}", reqtest_delete);
        return Err(format!("Erro no DELETE: {}", status_delete).into());
    }

    Ok(())
}