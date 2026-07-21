use serde::Deserialize;
#[derive(Deserialize, Debug)]
pub struct  TokenResponse{
    pub access_token: String,
}