
use std::time::Instant;

use reqwest::StatusCode;


pub async fn test_route_project_should_return_success() -> Result<(), Box<dyn std::error::Error>>{
    let inicio = Instant::now();
    let response = reqwest::get("http://localhost:8080/panelProjetos").await.unwrap();
    let status = response.status();
    if  status != StatusCode::OK{
        return  Err(format!(" O Java retornou Status: {}", status).into());
    }

    let duracao_search = inicio.elapsed();
    print!("Status: {}", status);
    println!(" | Rota do Projeto [OK].................. Latencia: {:.2?}", duracao_search);

    Ok(())
}