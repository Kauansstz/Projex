use serde::{Deserialize, Serialize};


#[derive(Debug, Serialize, Deserialize)]
pub enum Status {
    #[serde(rename = "EM_ANDAMENTO")]
    EmAndamento,

    #[serde(rename = "CONCLUIDO")]
    Concluido,

    #[serde(rename = "CANCELADO")]
    Cancelado,
}
