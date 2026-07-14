
use std::{collections::HashMap, env, fmt::format};

use dotenvy::dotenv;

pub async fn test_post_project_should_return_success() -> Result<(), Box<dyn ::std::error::Error>>{
    dotenv().ok();
    

    let client = reqwest::Client::new();

    println!("🔑 Autenicando na API do Java");


    Ok(())
}