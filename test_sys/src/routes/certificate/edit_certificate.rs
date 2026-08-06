use std::{env, fs};
use dotenvy::dotenv;
use reqwest::{Client, StatusCode, multipart::{Form, Part}, redirect::Policy};
use std::time::Instant;
use crate::{models::info_certificated::InfoCertificated, common::token::token};

pub async fn test_route_edit_certificated_should_return_success() -> Result<(), Box<dyn ::std::error::Error>>{
    dotenv().ok();
    let inicio = Instant::now();
    let api = env::var("API_URL").expect("API_URL não foi encontrada");
    let client = Client::builder().redirect(Policy::none()).build()?;

    let file_bytes = fs::read("E:\\projex\\test_sys\\src\\assets\\teste.pdf")?;
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

    let raw_json = response.text().await?;

    let certificado_criado: InfoCertificated = match serde_json::from_str(&raw_json) {
        Ok(certificado)=>{
            certificado
        }
        Err(e) =>{
            eprintln!("❌ Erro de Desserialização do Rust: {}", e);
            return  Err(e.into());
        }     
    };

    let certificado_id = &certificado_criado.id.expect("Java deveria ter retornado o ID do projeto");
    
    let route = client.get(format!("http://localhost:8080/{}/editar", certificado_id)).send().await?;
    let status_route = route.status();

    let _responde_delete = client
    .delete(format!("{}/delete/certificado/{}", api, certificado_id))
    .bearer_auth(token)
    .send()
    .await?;
    
    
    let duracao_projeto = inicio.elapsed();
    if status_route != StatusCode::OK{
        return  Err(format!("Status: {} | Rota de edicao de certificado [FALIED].... Latencia: {:.2?}", status_route, duracao_projeto).into());
    }else {
        print!("Status: {}", status_route);
        println!(" | Rota de edicao de certificado [OK].... Latencia: {:.2?}", duracao_projeto);
    }
    Ok(())
}