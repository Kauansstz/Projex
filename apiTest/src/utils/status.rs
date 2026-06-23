use serde::{Deserialize, Serialize};

#[derive(Debug, Serialize, Deserialize)]
pub enum Status {
    EM_ANDAMENTO,
    CONCLUIDO,
    CANCELADO,
}
