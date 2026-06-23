use crate::utils::status::Status;
use crate::models::tecnologia::Tecnologia;
use chrono::NaiveDate;
use serde::{Deserialize, Serialize};

#[derive(Debug, Serialize, Deserialize)]
pub struct InfoProject {
    pub id: Option<i64>,
    pub titulo: String,
    pub descricao: Option<String>,
    pub is_publish: bool,
    pub tecnologias_text: Option<String>,
    pub tecnologias: Vec<Tecnologia>,
    pub data_conclusao: Option<NaiveDate>,
    pub criado_em: Option<NaiveDate>,
    pub atualizado_em: Option<NaiveDate>,
    pub status: Status,
}