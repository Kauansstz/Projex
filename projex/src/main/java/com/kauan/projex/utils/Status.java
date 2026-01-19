package com.kauan.projex.utils;

public enum Status {
    EM_ANDAMENTO("text-yellow-500", "Em andamento"),
    CONCLUIDO("text-green-500", "Concluído"),
    CANCELADO("text-red-500", "Cancelado");

    private final String label;
    private final String cssClass;
    Status(String cssClass, String label){
        this.cssClass = cssClass;
        this.label = label;
    }

    public String getCssClass(){
        return cssClass;
    }
    public String getLabel(){
        return label;
    }
}

