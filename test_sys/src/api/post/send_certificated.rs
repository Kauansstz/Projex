use std::{env, thread, time::Duration, fs};
use dotenvy::dotenv;
use reqwest::{StatusCode, multipart::{Form, Part}};
use std::time::Instant;
use crate::{models::info_certificated::InfoCertificated, utils::{login::login, token::token}};

pub async fn test_post_certificated_should_return_success() -> Result<(), Box<dyn ::std::error::Error>>{
    dotenv().ok();
    let inicio = Instant::now();
    let api = env::var("API_URL").expect("API_URL não foi encontrada");
    let client = login().await?;

    thread::sleep(Duration::from_secs(1));

    let file_bytes = fs::read("E:\\projex\\test_sys\\src\\image\\teste.pdf")?;
    let file_part = Part::bytes(file_bytes)
    .file_name("teste.pdf")
    .mime_str("application/pdf")?;

    let dados = Form::new()
    .text("titulo", "Teste de criação de certificado")
    .text("descricao", "Teste de descriação de certificado")
    .text("isPublish", true.to_string())
    .text("instituicao", "Estacio")
    .text("category", "FINANCEIRO")
    .text("typeCertificate", "certificado")
    .text("status", "CONCLUIDO")
    .part("anexo", file_part)
    .text("dataConclusao", "2026-07-14")
    .text("criado_em", "2026-07-14")
    .text("donoId", 2.to_string() );

    let token = token().await.unwrap();
    let response = client
    .post(format!("{}/criarProjeto", api))
    .bearer_auth(token.clone())
    .multipart(dados)
    .send()
    .await?;

    let status = response.status();
    let raw_json = response.text().await?;
    if status != StatusCode::CREATED && status != StatusCode::OK {
        println!("Mensagem do erro do servidor: {}", raw_json);
    }

    let certificado_criado: InfoCertificated = match serde_json::from_str(&raw_json) {
        Ok(certificado)=>{
            certificado
        }
        Err(e) =>{
            eprintln!("❌ Erro de Desserialização do Rust: {}", e);
            eprintln!("Json bruto recebido: {}", raw_json);
            return  Err(e.into());
        }     
    };

    thread::sleep(Duration::from_secs(1));

    let certificado_id = &certificado_criado.id.expect("Java deveria ter retornado o ID do projeto");
    
    let responde_delete = client
    .delete(format!("{}/delete/certificado/{}", api, certificado_id))
    .bearer_auth(token)
    .send()
    .await?;
    
    let status_delete = responde_delete.status();
    let raw_json = responde_delete.text().await?;
    if !status_delete.is_success(){
        println!("❌ O Java retornou Status {}!", status_delete);
        println!("Mensagem do erro do servidor: {}", raw_json);
        return  Err(format!("Server returned status {}", status_delete ).into());
    }

    print!("Status: {}", status);
    let duracao_projeto = inicio.elapsed();
    println!(" | Criar e deletar certificados [OK]..... Latencia: {:.2?}", duracao_projeto);
    Ok(())
}