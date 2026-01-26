package com.kauan.projex.utils;

public enum Category {
    FINANCEIRO("Financeiro"),
    CONTABIL("Contábil"),
    JURIDICO("Jurídico"),
    COMPRAS("Compras"),
    ADMINISTRATIVO("Administrativo"),
    RH("Recursos Humanos"),
    DEPARTAMENTO_PESSOAL("Departamento Pessoal"),
    TREINAMENTO("Treinamento"),
    HARDWARE("Hardware"),
    SOFTWARE("Software"),
    INFRAESTRUTURA("Infraestrutura"),
    REDES("Redes"),
    SEGURANCA_DA_INFORMACAO("Segurança da Informação"),
    SUPORTE_TI("Suporte de TI"),
    DESENVOLVIMENTO("Desenvolvimento"),
    MARKETING("Marketing"),
    DESIGN("Design"),
    UX_UI("UX / UI"),
    MIDIAS_SOCIAIS("Mídias Sociais"),
    COMERCIAL("Comercial"),
    VENDAS("Vendas"),
    POS_VENDA("Pós-venda"),
    ATENDIMENTO_CLIENTE("Atendimento ao Cliente"),
    LOGISTICA("Logística"),
    OPERACIONAL("Operacional"),
    MANUTENCAO("Manutenção");

    private final String label;
    Category(String label){
        this.label = label;
    }

    public String getLabel(){
       return label;
    }

}
