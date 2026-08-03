use dotenvy::dotenv;
use std::thread;
use std::time::Duration;
use std::time::{SystemTime, UNIX_EPOCH};
use std::env;
use crate::models::info_user::InfoUser;
use crate::utils::token::token;
use std::time::Instant;


pub async fn test_post_users_and_delete_should_return_success() -> Result<(), Box<dyn std::error::Error>> {
    dotenv().ok();
    let inicio = Instant::now();
    let api = env::var("API_URL").expect("API_URL não encontrada");
    let client = reqwest::Client::new();

    let token = token().await.unwrap();
    let timestemp = SystemTime::now()
    .duration_since(UNIX_EPOCH)
    .unwrap()
    .as_secs()
    .to_string();

    let cpf_dinamico = format!("999{}", &timestemp[0..8]);
    let email_dinamico = format!("teste.teste.{}@gmail.com", timestemp);

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
        .post(format!("{}/create/usuario", api))
        .bearer_auth(token.clone())
        .json(&dados)
        .send()
        .await?;
    
    let status = response.status();
    let raw_json = response.text().await?;
    thread::sleep(Duration::from_secs(1));
    if status.clone() != reqwest::StatusCode::OK && status.clone() != reqwest::StatusCode::CREATED{
        println!("❌ O Java retornou Status {}!", &status.clone());
        println!("Mensagem do erro do servidor: {}", raw_json);
        
        return Err(format!("Server returned status {}", &status.clone()).into());
    }

    
    let usuario_criado:InfoUser= match serde_json::from_str::<InfoUser>(&raw_json){
        Ok(user)=> {
            user
        }
        Err(e)=> {
            eprintln!("\n❌ Erro de Desserialização no Rust (Esperava objeto único): {}", e);
            println!("JSON bruto recebido: {}", raw_json);
            return Err(e.into());
        }
    };
    thread::sleep(Duration::from_secs(1));
    
    assert!(&status.is_success().clone(), "A API Java não retornou um status de sucesso!");
    let usuario_id = &usuario_criado.id.expect("O Java deveria ter retornado o ID do usuário.");

    thread::sleep(Duration::from_secs(1));

    let response_delete = client
    .delete(format!("{}/delete/user/{}", api, usuario_id))
    .bearer_auth(token)
    .send()
    .await?;

    let status_delete = response_delete.status();

    thread::sleep(Duration::from_secs(1));
    if status_delete.is_success() {
        print!("Status: {}", status)
    } else {
        eprintln!("❌ Falha ao limpar o banco. Resposta do servidor: {}", status_delete);
        return Err(format!("Erro no DELETE: {} ", status_delete).into());
    }
    let duracao_user = inicio.elapsed();
    println!(" | Criar e deletar usuário [OK]................ Latencia: {:.2?}", duracao_user);

    Ok(())
}