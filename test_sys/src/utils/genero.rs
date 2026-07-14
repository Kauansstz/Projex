use serde::{Deserialize, Serialize};

#[derive(Debug, Serialize, Deserialize)]
pub enum Genero {
    MASCULINO,
    FEMININO,
    OUTRO,
}