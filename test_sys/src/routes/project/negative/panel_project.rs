use std::time::Instant;

use reqwest::{StatusCode, redirect::Policy};

pub async fn test_route_panel_project_should_return_401() -> Result<(), Box<dyn std::error::Error>>{
    let inicio = Instant::now();
    let client = reqwest::Client::builder().redirect(Policy::none()).build()?;
    let response = client.get("http://localhost:8080/panelProjetos").send().await?;
    let status = response.status();
    
    let duracao_search = inicio.elapsed();
    if  status == StatusCode::OK{
        return  Err(format!("Status: {} | Rota do Projeto [FALIED].................. Latencia: {:.2?}", status,duracao_search).into());
    }else {
        print!("Status: {}", status);
        println!(" | Rota do Projeto [OK]............... Latencia: {:.2?}", duracao_search);
    }

    Ok(())
}