use std::time::Instant;

use reqwest::StatusCode;

use crate::common::login::login;


pub async fn test_route_panel_users_should_return_success() -> Result<(), Box<dyn std::error::Error>>{
    let inicio = Instant::now();
    let client = login().await.unwrap();
    let response =  client.get("http://localhost:8080/panelUser").send().await?;
    let status = response.status();
    
    let duracao = inicio.elapsed(); 
    if status != StatusCode::OK {
        return  Err(format!("Status: {} | Rota da central de users [FALIED]...... Latencia: {:.2?}", status, duracao).into());
    }else {
        print!("Status: {}", status);
        println!(" | Rota da central de users [OK]......... Latencia: {:.2?}", duracao);
    }

    Ok(())
}