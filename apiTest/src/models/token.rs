use serde::Deserialize;
#[derive(Deserialize, Debug)]
pub struct  TokenResponse{
    pub access_token: String,
    pub token_type: String,
    pub expires_in:u32,
}