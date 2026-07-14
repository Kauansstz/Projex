use serde::{Deserialize, Serialize};

#[derive(Debug, Serialize, Deserialize)]
pub enum TipoLink {
    GITHUB,
    LINKEDIN,
    SITE,
    INSTAGRAM,
    YOUTUBE,
    OUTRO,
}