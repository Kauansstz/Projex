use crate::{models::info_user, utils::tipo_link::TipoLink};
use serde::{Deserialize, Serialize};
#[derive(Debug, Serialize, Deserialize)]
pub struct LinkUsuario {
    pub id: Option<i64>,
    pub url: String,
    pub tipo_link: TipoLink,
    pub usuario: info_user::InfoUser,
} 