package com.kauan.projex.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UsuarioLogado implements UserDetails {
    
    private final String email;
    private final String senha;
    private final List<GrantedAuthority> autoridades;

    // Construtor completo
    public UsuarioLogado(String email, String senha, List<GrantedAuthority> autoridades) {
        this.email = email;
        this.senha = senha;
        this.autoridades = autoridades;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.autoridades;
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getEmail() {
        return this.email;
    }
}