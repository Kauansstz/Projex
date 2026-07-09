mod api;
mod models;
mod utils;


#[tokio::main]
async fn main(){
    println!("Iniciado a validação da API de busca de cliente");

    if let Err(e) = api::get::search_account::
    test_get_users_should_return_success().await{
        eprintln!("Teste 'test_get_users_should_return_success' falhou: {} ", e);
    } else{
        println!("Teste 'test_get_users_should_return_success' passou! ");
    }

    if let Err(e) = api::post::send_user::test_post_users_should_return_success().await {
        eprintln!("❌ Teste 'test_post_users_should_return_success' falhou: {}", e);
    } else {
        println!("✅ Teste 'test_post_users_should_return_success' passou!");
    }
    if let Err(e) = api::post::token::test_post_token_should_return_success().await {
        eprintln!("❌ Teste 'test_post_token_should_return_success' falhou: {}", e);
    } else {
        println!("✅ Teste 'test_post_token_should_return_success' passou!");
    }

}