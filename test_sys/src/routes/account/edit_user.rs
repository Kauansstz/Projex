use dotenvy::dotenv;
use reqwest::{Client, StatusCode, redirect::Policy};
use std::time::Instant;

pub async fn test_route_edit_user_should_return_success() -> Result<(), Box<dyn ::std::error::Error>>{
    dotenv().ok();
    let inicio = Instant::now();
    let client = Client::builder().redirect(Policy::none()).build()?;
    
    let route = client.get(format!("http://localhost:8080/{}/editar", 2)).send().await?;
    let status_route = route.status();
    
    let duracao_projeto = inicio.elapsed();
    if status_route != StatusCode::OK {
        return  Err(format!("Status: {} | Rota de edicao de usuario [FALIED]... Latencia: {:.2?}", status_route, duracao_projeto).into());
    }else {
        print!("Status: {}", status_route);
        println!(" | Rota de edicao de usuario [OK].... Latencia: {:.2?}", duracao_projeto);
    }
    Ok(())
}