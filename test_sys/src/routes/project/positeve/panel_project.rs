
use std::time::Instant;

use reqwest::{Client, StatusCode, redirect::Policy};


pub async fn test_route_project_should_return_success() -> Result<(), Box<dyn std::error::Error>>{
    let inicio = Instant::now();
    let client = Client::builder()
    .redirect(Policy::none())
    .build()?;
    let response = client.get("http://localhost:8080/panelProjetos").send().await?;
    let status = response.status();
    
    let duracao_search = inicio.elapsed();
    if  status != StatusCode::OK{
        return  Err(format!("Status: {} | Rota do Projeto [OK].................. Latencia: {:.2?}", status,duracao_search).into());
    }else {
        print!("Status: {}", status);
        println!(" | Rota do Projeto [OK].................. Latencia: {:.2?}", duracao_search);
    }

    Ok(())
}