use std::{thread, time::Duration};
mod api;
mod models;
mod utils;
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
    if let Err(e) = api::get::search_account::
    test_get_users_should_return_success().await{
        eprintln!(" | Pesquisar usuário falhou: {}", e);
    } else{
        //Continue
    }

    if let Err(e) = api::post::send_user::test_post_users_and_delete_should_return_success().await {
        eprintln!(" | Criar e deletar usuário falhou: {}", e);
    } else {
        //Continue
    }
    if let Err(e) = api::post::token::test_post_token_should_return_success().await {
        eprintln!(" | Criar token falhou: {}", e);
    } else {
        //Continue
    }
    if let Err(e) = api::post::send_project::test_post_project_should_return_success().await {
        eprintln!(" | Criar e deletar projeto falhou: {}", e);
    } else {
        //Continue
    }

    let duracao = inicio.elapsed();
    println!("Total de latencia: {:.2?}", duracao);
}