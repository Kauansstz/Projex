use std::{thread, time::Duration};
mod domain;
mod models;
mod routes;
mod common;
use std::process::Command;
use std::time::Instant;

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
    let inicio = Instant::now();
    println!("Iniciando os testes de api, aguarde...");
    if let Err(e) = domain::account::search_account::
    test_get_users_should_return_success().await{
        eprintln!(" | Pesquisar usuário falhou: {}", e);
    } else{
        //Continue
    }

    if let Err(e) = domain::account::send_user::test_post_users_and_delete_should_return_success().await {
        eprintln!(" | Criar e deletar usuário falhou: {}", e);
    } else {
        //Continue
    }
    if let Err(e) = domain::auth::token::test_post_token_should_return_success().await {
        eprintln!(" | Criar token falhou: {}", e);
    } else {
        //Continue
    }
    if let Err(e) = domain::project::send_project::test_post_project_should_return_success().await {
        eprintln!(" | Criar e deletar projeto falhou: {}", e);
    } else {
        //Continue
    }
    if let Err(e) = domain::certificate::send_certificated::test_post_certificated_should_return_success().await {
        eprintln!(" | Criar e deletar certificado falhou: {}", e);
    } else {
        //Continue
    }
    println!("Finalizando os testes de api.");
    print!("");
    println!("Iniciando os testes de rotas, aguarde...");

    if let Err(e) = routes::home::test_rota_dashboard_should_return_success().await{
        eprint!("Rota para o dashboard falhou: {}", e)
    }else {
        //
    }

    let duracao = inicio.elapsed();
    println!("Total de latencia: {:.2?}", duracao);
}