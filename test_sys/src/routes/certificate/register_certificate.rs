use std::time::Instant;

use reqwest::{Client, StatusCode, redirect::Policy};


pub async fn test_route_register_certificate_should_return_success() -> Result<(), Box<dyn std::error::Error>>{
    let inicio = Instant::now();
    let client = Client::builder()
    .redirect(Policy::none())
    .build()?;
    let response =  client.get("http://localhost:8080/certificateCreate").send().await?;
    let status = response.status();
    
    let duracao = inicio.elapsed(); 
    
    if status != StatusCode::OK {
        return  Err(format!("Status: {} | Rota do cadastro de certificado [FALIED].. Latencia: {:.2?}", status, duracao).into());
    }
    else {
        print!("Status: {}", status);    
        println!(" | Rota do cadastro de certificado [OK].. Latencia: {:.2?}", duracao);
    }

    Ok(())
}