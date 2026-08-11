
use std::time::Instant;

use reqwest::StatusCode;

use crate::common::login::login;


pub async fn test_route_questions_and_response_should_return_success() -> Result<(), Box<dyn std::error::Error>>{
    let inicio = Instant::now();
    let client = login().await.unwrap();
    let response = client.get("http://localhost:8080/centralPerguntas").send().await?;
    let status = response.status();
    
    let duracao_search = inicio.elapsed();
    if  status != StatusCode::OK{
        return  Err(format!("Status: {} | Rota das Questões [FALIED]................ Latencia: {:.2?}", status,duracao_search).into());
    }else {
        print!("Status: {}", status);
        println!(" | Rota das Questões [OK]................ Latencia: {:.2?}", duracao_search);
    }

    Ok(())
}