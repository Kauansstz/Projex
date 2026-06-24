use dotenvy::dotenv;

pub async fn test_post_project_should_return_success() -> Result<(), Box<dyn ::std::error::Error>>{
    dotenv().ok();
    
    Ok(())
}