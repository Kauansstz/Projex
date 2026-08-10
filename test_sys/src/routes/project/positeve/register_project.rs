use std::time::Instant;

use reqwest::{StatusCode};

use crate::common::login::login;


pub async fn test_route_register_project_should_return_success() -> Result<(), Box<dyn std::error::Error>>{
    let inicio = Instant::now();
    let client = login().await.unwrap();
    let response =  client.get("http://localhost:8080/createdProject").send().await?;
    let status = response.status();
    
    let duracao = inicio.elapsed(); 
    if status != StatusCode::OK {
        return  Err(format!("Status: {} | Rota do cadastro de projeto [OK]...... Latencia: {:.2?}", status,duracao).into());
    }else {
        print!("Status: {}", status);
        println!(" | Rota do cadastro de projeto [OK]...... Latencia: {:.2?}", duracao);
    }

    Ok(())
}