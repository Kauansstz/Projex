use crate::common::status::Status;
use crate::common::category::Category;
use chrono::{NaiveDate, NaiveDateTime};
use serde::{Deserialize, Serialize};

#[derive(Debug, Serialize, Deserialize)]
#[serde(rename_all = "camelCase")]
pub struct InfoCertificated {
    pub id: Option<i64>,
    pub titulo: String,
    pub instituicao: String,
    pub descricao: Option<String>,
    pub anexo: Option<String>,
    pub is_publish: bool,
    pub status: Status,
    pub typeCertificate: String,
    pub category: Category,
    pub dataConclusao: Option<NaiveDate>,
    pub update: NaiveDateTime,
    pub criado_em: NaiveDateTime,
    #[serde(skip_serializing_if = "Option::is_none")]
    pub dono_id: Option<i32>,
}