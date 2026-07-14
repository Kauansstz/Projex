use crate::utils::genero::Genero;
use crate::models::link_usuario::LinkUsuario;
use crate::models::info_project::InfoProject;
use serde::{Deserialize, Serialize};
use chrono::{DateTime, NaiveDate, NaiveDateTime, Utc};

#[allow(non_snake_case)]
#[derive(Debug, Serialize, Deserialize)]
#[serde(rename_all = "camelCase")]
pub struct InfoUser {
    pub id: Option<i64>,
    pub name: String,
    pub dataNasc: NaiveDate,
    pub password: String,
    pub confirmPassword: String,
    pub genero: Genero,
    pub cpf: String,
    pub telefone: Option<String>,
    pub descricao: Option<String>,
    pub sobre: Option<String>,
    pub criadoEm: Option<DateTime<Utc>>,
    pub ativo: bool,
    pub ultimoLogin: Option<NaiveDateTime>,
    pub atualizadoEm: Option<NaiveDateTime>,
    pub tentativasLogin: i32,
    pub role: String,
    pub token: String,
    pub area: Option<String>,
    pub tecnologiasText: Option<String>,
    pub resetTokenExpiracao: String,
    pub fotoPerfil: Option<String>,
    pub ipCriacao: String,
    pub ipUltimoLogin: String,
    pub forcarTrocaSenha: Option<bool>,
    pub email: String,
    pub aceitarTermos: bool,
    pub projetos: Vec<InfoProject>,
    pub nameUser: Option<String>,
    pub inativadoPor: Option<i64>,
    pub cargo: Option<String>,
    pub empresa: Option<String>,
    pub link: Vec<LinkUsuario>,
    pub dataInativacao: Option<NaiveDateTime>,
}