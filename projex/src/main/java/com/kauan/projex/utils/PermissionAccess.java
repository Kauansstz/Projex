package com.kauan.projex.utils;

import org.springframework.security.core.GrantedAuthority;

public enum PermissionAccess implements GrantedAuthority{
    ROLE_DEFAULT,
    ROLE_ADMIN,
    ROLE_MODERADOR;

    @Override
    public String getAuthority(){
        return name();
    } 
}
