
use std::{collections::HashMap, env};

use dotenvy::dotenv;
use crate::models::token::TokenResponse;

pub async fn test_post_token_should_return_success() -> Result<(), Box<dyn ::std::error::Error>>{
    dotenv().ok();
    
    let api = env::var("API_URL").expect("API_URL não foi encontrada");
    let client_id =  env::var("CLIENT_ID").expect("CLIENT_ID não foi encontrado");
    let client_secret = env::var("CLIENT_SECRET").expect("CLIENT_SECRET não foi encontrado");
    let token_url = env::var("TOKEN_URL").expect("TOKEN_URL não foi encontrado");

    let client = reqwest::Client::new();

    println!("🔑 Autenicando na API do Java");

    let mut params = HashMap::new();
    params.insert("grant_type", "client_credentials");
    params.insert("client_id", &client_id );
    params.insert("client_secret", &client_secret);

    let token_response = client
        .post(&token_url)
        .form(&params)
        .send()
        .await?;

    if !token_response.status().is_success(){
        eprint!("Erro ao obter o token: {}", token_response.status());
        let error_body = token_response.text().await?;
        eprint!("Detalhes: {}", error_body);
        std::process::exit(1);
    }

    let token_data: TokenResponse = token_response.json().await?;
    println!("Toke obtido com sucesso! Tipo: {}", token_data.token_type);
    println!("----------------------------------------------------------");
    println!("Chamando a API principal: {}...", api);

    let api_response = client
        .get(&api)
        .header("Authorization", format!("Bearer {}", token_data.access_token))
        .header("Content-Type", "application/json")
        .send()
        .await?;

    let status = api_response.status();


    if status == 404 {
        let erro_body: serde_json::Value = api_response.json().await?;
        println!("Error: {:?}", erro_body);
    }

    println!("Status da Resposta: {}", &status);
    

    Ok(())
}