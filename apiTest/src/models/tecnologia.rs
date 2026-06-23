use serde::{Deserialize, Serialize};
#[derive(Debug, Serialize, Deserialize)]
pub struct Tecnologia{
    id:u32,
    nome: String
}