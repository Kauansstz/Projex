use crate::{models::info_user, common::tipo_link::TipoLink};
use serde::{Deserialize, Serialize};

#[allow(non_snake_case)]
#[derive(Debug, Serialize, Deserialize)]
#[serde(rename_all = "camelCase")]
pub struct LinkUsuario {
    pub id: Option<i64>,
    pub url: String,
    pub tipoLink: Option<TipoLink>,
    pub usuario: info_user::InfoUser,
}