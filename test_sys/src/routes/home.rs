use std::time::Instant;

pub async fn test_rota_dashboard_should_return_success() -> Result<(), Box<dyn std::error::Error>> {
    let inicio = Instant::now();
    let response = reqwest::get("http://localhost:8080/home")
        .await
        .unwrap();

    let status = response.status();
    if status != reqwest::StatusCode::OK {
        let error_body = response.text().await?;
        println!("❌ O Java retornou Status {}!", status);
        println!("Mensagem do erro do servidor: {}", error_body);
        
        return Err(format!("Server returned status {}", status).into());
    }
    let duracao_search = inicio.elapsed();
    print!("Status: {}", status);
    println!(" | Rota do Dashboard [OK]................ Latencia: {:.2?}", duracao_search);
    Ok(())
}