
use std::{collections::HashMap, env};
use std::sync::OnceLock;
use dotenvy::dotenv;
use crate::models::token::TokenResponse;

static TOKEN: OnceLock<String> = OnceLock::new();

pub async fn token() -> Result<String, reqwest::Error>{
    dotenv().ok();
    
    if let Some(token_salvo)= TOKEN.get(){
        return  Ok(token_salvo.clone());
    }

    let client_id =  env::var("CLIENT_ID").expect("CLIENT_ID não foi encontrado");
    let client_secret = env::var("CLIENT_SECRET").expect("CLIENT_SECRET não foi encontrado");
    let token_url = env::var("TOKEN_URL").expect("TOKEN_URL não foi encontrado");
    let client = reqwest::Client::new();


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
    let token_str = token_data.access_token.clone();
    let _ = TOKEN.set(token_data.access_token);
    Ok(token_str)
}