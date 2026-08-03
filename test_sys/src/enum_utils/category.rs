use std::fmt;
use serde::{Serialize, Deserialize};

#[derive(Debug, Clone, Copy, PartialEq, Eq, Hash, Serialize, Deserialize)]
#[serde(rename_all = "SCREAMING_SNAKE_CASE")]
pub enum Category {
    Financeiro,
    Contabil,
    Juridico,
    Compras,
    Administrativo,
    Rh,
    DepartamentoPessoal,
    Treinamento,
    Hardware,
    Software,
    Infraestrutura,
    Redes,
    SegurancaDaInformacao,
    SuporteTi,
    Desenvolvimento,
    Marketing,
    Design,
    UxUi,
    MidiasSociais,
    Comercial,
    Vendas,
    PosVenda,
    AtendimentoCliente,
    Logistica,
    Operacional,
    Manutencao,
}

impl Category {
    pub fn label(&self) -> &'static str {
        match self {
            Category::Financeiro => "Financeiro",
            Category::Contabil => "Contábil",
            Category::Juridico => "Jurídico",
            Category::Compras => "Compras",
            Category::Administrativo => "Administrativo",
            Category::Rh => "Recursos Humanos",
            Category::DepartamentoPessoal => "Departamento Pessoal",
            Category::Treinamento => "Treinamento",
            Category::Hardware => "Hardware",
            Category::Software => "Software",
            Category::Infraestrutura => "Infraestrutura",
            Category::Redes => "Redes",
            Category::SegurancaDaInformacao => "Segurança da Informação",
            Category::SuporteTi => "Suporte de TI",
            Category::Desenvolvimento => "Desenvolvimento",
            Category::Marketing => "Marketing",
            Category::Design => "Design",
            Category::UxUi => "UX / UI",
            Category::MidiasSociais => "Mídias Sociais",
            Category::Comercial => "Comercial",
            Category::Vendas => "Vendas",
            Category::PosVenda => "Pós-venda",
            Category::AtendimentoCliente => "Atendimento ao Cliente",
            Category::Logistica => "Logística",
            Category::Operacional => "Operacional",
            Category::Manutencao => "Manutenção",
        }
    }
}

impl fmt::Display for Category {
    fn fmt(&self, f: &mut fmt::Formatter<'_>) -> fmt::Result {
        write!(f, "{}", self.label())
    }
}