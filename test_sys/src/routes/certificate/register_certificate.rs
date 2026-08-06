use std::time::Instant;

use reqwest::StatusCode;


pub async fn test_route_register_certificate_should_return_success() -> Result<(), Box<dyn std::error::Error>>{
    let inicio = Instant::now();
    let response =  reqwest::get("http://localhost:8080/certificateCreate").await.unwrap();
    let status = response.status();
    if status != StatusCode::OK {
        return  Err(format!(" O Java retornou Status: {}", status).into());
    }

    let duracao = inicio.elapsed(); 
    print!("Status: {}", status);
    println!(" | Rota do cadastro de certificado [OK].. Latencia: {:.2?}", duracao);

    Ok(())
}