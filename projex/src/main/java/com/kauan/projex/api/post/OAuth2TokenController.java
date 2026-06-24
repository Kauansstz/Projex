package com.kauan.projex.api.post;

import java.util.Map;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.kauan.projex.service.TokenService;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/access-token/realm")
public class OAuth2TokenController {
    
    @Autowired
    private TokenService tokenService;

    private final String CLIENT_ID_VALIDO = "projex";
    private final String CLIENT_SECRET_VALIDO = "projext-secret-123";

    @PostMapping("projex-service-realm")
    public ResponseEntity<?> gerarTokenOAuth2(
        @RequestParam("grant_type") String grantType,
        @RequestParam("client_id") String clientId,
        @RequestParam(value = "client_secret", required = false) String clientSecret
    ){
        if (!CLIENT_ID_VALIDO.equals(clientId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "invalid_client", "error_description", "Client ID inválido."));
        }

        String token = tokenService.gerarToken(clientId);
        return ResponseEntity.ok(Map.of(
            "access_token", token,
            "token_type", "Bearer",
            "expires_in", 7200
        ));
    }
}
