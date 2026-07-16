use std::env;

use dotenvy::dotenv;
use reqwest::Client;


pub async fn login() -> Result<Client, Box<dyn ::std::error::Error>>{
    dotenv().ok();
    let api = env::var("API_URL").expect("API_URL não foi encontrada");
    let user_auth = env::var("USER_AUTH").expect("USER_AUTH não foi encontrado");
    let senha = env::var("PASSWORD").expect("PASSWORD não foi encontrado");

    let client = reqwest::Client::builder()
    .cookie_store(true)
    .build()?;

    let dados = serde_json::json!(
        {
            "email": user_auth,
            "password": senha
        }
    );

    let response = client
    .post(format!("{}/email/login", api))
    .json(&dados)
    .send()
    .await?;

    let status = response.status();
    let raw_json = response.text().await?;
    if !status.is_success() {
        eprintln!("Ocorreu algum erro");
        eprintln!("Status da requisicao: {}", status);
        return Err(format!("Mensagem de retorno do servidor: {}", raw_json).into());
    }
    Ok(client)
}