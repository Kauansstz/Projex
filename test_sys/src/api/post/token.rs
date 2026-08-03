
use std::thread;
use std::time::Duration;
use std::{collections::HashMap, env};
use dotenvy::dotenv;
use std::time::Instant;

pub async fn test_post_token_should_return_success() -> Result<(), Box<dyn ::std::error::Error>>{
    dotenv().ok();  
    let inicio = Instant::now();
    let client_id =  env::var("CLIENT_ID").expect("CLIENT_ID não foi encontrado");
    let client_secret = env::var("CLIENT_SECRET").expect("CLIENT_SECRET não foi encontrado");
    let token_url = env::var("TOKEN_URL").expect("TOKEN_URL não foi encontrado");

    let client = reqwest::Client::new();

    thread::sleep(Duration::from_secs(1));
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
        eprint!("Erro ao obter o token: {}", token_response.status().clone());
        let error_body = token_response.text().await?;
        eprint!("Detalhes: {}", error_body);
        std::process::exit(1);
    }
    thread::sleep(Duration::from_secs(1));

    print!("Status: {}", &token_response.status());
    let duracao_token = inicio.elapsed();
    println!(" | Criar token [OK]................ Latencia: {:.2?}", duracao_token);

    

    Ok(())
}