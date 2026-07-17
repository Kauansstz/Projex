use crate::utils::loading::loading;

mod api;
mod models;
mod utils;


#[tokio::main]
async fn main(){
    println!("{:-<60}", "");
    println!("|              Iniciado a validação do sistema             |");
    println!("{:-<60}", "");
    loading();
    println!("{:=<60}", "");
    if let Err(e) = api::get::search_account::
    test_get_users_should_return_success().await{
        eprintln!("Teste de pesquisar usuário falhou: {} ", e);
    } else{
        println!("Teste de pesquisar usuário [OK]");
    }

    if let Err(e) = api::post::send_user::test_post_users_and_delete_should_return_success().await {
        println!("");
        eprintln!("❌ Teste criacao e deletar usuário falhou: {}", e);
        println!("");
    } else {
        println!("");
        println!("✅ Teste criacao e delear usuário [OK]");
        println!("");
    }
    if let Err(e) = api::post::token::test_post_token_should_return_success().await {
        println!("");
        eprintln!("❌ Teste de criacao de Token falhou: {}", e);
        println!("");
    } else {
        println!("");
        println!("✅ Teste de criacao de Token [OK]");
        println!("");
    }
    if let Err(e) = api::post::send_project::test_post_project_should_return_success().await {
        println!("");
        eprintln!("❌ Teste de criacao e deletar projeto falhou: {}", e);
        println!("");
    } else {
        println!("");
        println!("✅ Teste de criacao e deletar projeto [OK]");
        println!("");
    }

}