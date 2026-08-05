use std::{thread, time::Duration};
mod domain;
mod models;
mod routes;
mod common;
use std::{process::Command,time::Instant, future::Future, pin::Pin};
use crate::domain::{
    account::search_account::test_get_users_should_return_success,
    account::send_user::test_post_users_and_delete_should_return_success, 
    auth::token::test_post_token_should_return_success,
    certificate::send_certificated::test_post_certificated_should_return_success,
    project::send_project::test_post_project_should_return_success
};
use crate::routes::{
    home::test_rota_dashboard_should_return_success, 
    certificate::test_route_certificate_should_return_success, 
    project::test_route_project_should_return_success,
    questions_and_response::test_route_questions_and_response_should_return_success
};

fn clear_terminal() {
    if cfg!(target_os = "windows") {
        Command::new("cmd")
            .args(["/C", "cls"])
            .status()
            .unwrap();
    } else {
        Command::new("clear")
            .status()
            .unwrap();
    }
}


#[tokio::main]
async fn main(){
    clear_terminal();
    thread::sleep(Duration::from_secs(1));
    let test_system_art = r#"
        ██████  ███████  ███████  ███████     ███████  ██    ██  ███████  ████████  ███████  ███    ███ 
          ██    ██       ██          ██       ██        ██  ██   ██          ██     ██       ████  ████ 
          ██    █████    ███████     ██       ███████    ████    ███████     ██     █████    ██ ████ ██ 
          ██    ██            ██     ██            ██     ██          ██     ██     ██       ██  ██  ██ 
          ██    ███████  ███████     ██       ███████     ██     ███████     ██     ███████  ██      ██ 
        "#;
    println!("{}", test_system_art);
    println!("{:=<120}", "");
    type TestResult = Result<(), Box<dyn std::error::Error>>;
    type TestFuture = Pin<Box<dyn Future<Output = TestResult> + Send>>;
    type TestFn = Box<dyn Fn() -> TestFuture + Send + Sync>;

    let categorias: Vec<(&str, Vec<TestFn>)> = vec![
    (
        "ENDPOINTS DE AUTENTICACAO E USUARIOS",
        vec![
            Box::new(|| {
                Box::pin(test_post_users_and_delete_should_return_success())
            }),
            Box::new(|| {
                Box::pin(test_get_users_should_return_success())
            }),
            Box::new(|| {
                Box::pin(test_post_token_should_return_success())
            }),
        ],
        ),
        (
            "ENDPOINTS DE RECURSOS",
            vec![
                Box::new(|| {
                    Box::pin(test_post_project_should_return_success())
                }),
                Box::new(|| {
                    Box::pin(test_post_certificated_should_return_success())
                }),
            ],
        ),
        (
            "ROTAS DE NAVEGACAO E DASHBOARD",
            vec![
                Box::new(|| {
                    Box::pin(test_rota_dashboard_should_return_success())
                }),
                Box::new(|| {
                    Box::pin(test_route_certificate_should_return_success())
                }),
                Box::new(|| {
                    Box::pin(test_route_project_should_return_success())
                }),
                Box::new(|| {
                    Box::pin(test_route_questions_and_response_should_return_success())
                }),
            ],
        ),
    ];

    let inicio = Instant::now();
    let mut teste_return_success = 0;
    let mut teste_return_error = 0;
    for (indice, (titulo, testes)) in categorias.iter().enumerate(){
        println!();
        println!("[{}/{}] {}", indice + 1, categorias.len(), titulo);
        for teste in testes{    
            match teste().await{
                Ok(_) =>{
                    teste_return_success += 1;
                }
                Err(e) =>{
                    teste_return_error += 1;
                    eprintln!("❌ Teste falhou: {}", e);
                }
            }
        }
        
    }

    let duracao = inicio.elapsed();
    println!();
    println!("{:=<120}", "");
    println!("RESUMO DOS TESTES");
    println!();
    println!("Quantidade de testes que deram sucesso: {}", teste_return_success);
    println!("Quantidade de testes que deram erro: {}", teste_return_error);
    println!("Tempo total dos testes: {:.2?}", duracao);
    println!();
    if teste_return_error >= 1  {
        println!("STATUS: TESTES FALHARAM");
        println!("{:=<120}", "");
        std::process::exit(1);
    }
    println!("STATUS: TODOS OS TESTES PASSARAM");
    

    println!("{:=<120}", "");
}