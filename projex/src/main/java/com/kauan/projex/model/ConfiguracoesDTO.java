package com.kauan.projex.model;

public class ConfiguracoesDTO {
     private Boolean modoEscuro;
    private String idioma;
    private Boolean notifEmail;
    private Boolean notifWhatsapp;
    private String email;
    private String whatsapp;

    // Getters e Setters
    public Boolean getModoEscuro() { return modoEscuro; }
    public void setModoEscuro(Boolean modoEscuro) { this.modoEscuro = modoEscuro; }

    public String getIdioma() { return idioma; }
    public void setIdioma(String idioma) { this.idioma = idioma; }

    public Boolean getNotifEmail() { return notifEmail; }
    public void setNotifEmail(Boolean notifEmail) { this.notifEmail = notifEmail; }

    public Boolean getNotifWhatsapp() { return notifWhatsapp; }
    public void setNotifWhatsapp(Boolean notifWhatsapp) { this.notifWhatsapp = notifWhatsapp; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getWhatsapp() { return whatsapp; }
    public void setWhatsapp(String whatsapp) { this.whatsapp = whatsapp; }
}
