use dotenvy::dotenv;
use reqwest::StatusCode;
use std::time::Instant;
use crate::common::login::login;

pub async fn test_route_edit_user_should_return_success() -> Result<(), Box<dyn ::std::error::Error>>{
    dotenv().ok();
    let inicio = Instant::now();
    let client = login().await?;
    
    let route = client.get(format!("http://localhost:8080/{}/editar", 2)).send().await?;
    let status_route = route.status();
    if status_route != StatusCode::OK {
        return Err(format!(" O Java retornou Status: {}", status_route).into());
    }

    

    print!("Status: {}", status_route);
    let duracao_projeto = inicio.elapsed();
    println!(" | Rota de edicao de certificado [OK].... Latencia: {:.2?}", duracao_projeto);
    Ok(())
}