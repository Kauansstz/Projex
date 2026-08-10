
use std::time::Instant;

use reqwest::StatusCode;

use crate::common::login::login;


pub async fn test_route_certificate_should_return_success() -> Result<(), Box<dyn std::error::Error>>{
    let inicio = Instant::now();
    let client = login().await.unwrap();
    let response = client.get("http://localhost:8080/panelCertificados").send().await?;
    let status = response.status();
    let duracao_search = inicio.elapsed();
    if  status != StatusCode::OK{
        let erro = response.text().await?;
        return  Err(format!("Status: {} | Rota do Certificado [FALIED]....... Latencia: {:.2?}\nErro: \n{}", status,duracao_search, erro).into());
    }
    else {
        print!("Status: {}", status);
        println!(" | Rota do Certificado [OK].............. Latencia: {:.2?}", duracao_search);
    }

    Ok(())
}