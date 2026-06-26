package com.kauan.projex.api.post;

import java.util.Map;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.kauan.projex.service.TokenService;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/access-token/realm")
public class OAuth2TokenController {
    
    @Autowired
    private TokenService tokenService;

    @Value("${api.security.token.client_id_valido}")
    private  String client_id_valido;

    @Value("${api.security.token.client_secret_valido}")
    private  String client_secret_valido ;

    @PostMapping("/projex-service-realm")
    public ResponseEntity<?> gerarTokenOAuth2(
        @RequestParam("grant_type") String grantType,
        @RequestParam("client_id") String clientId,
        @RequestParam(value = "client_secret", required = false) String clientSecret
    ){
        if (!client_id_valido.equals(clientId) || !client_secret_valido.equals(clientSecret)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of(
                        "error", "invalid_client", 
                        "error_description", "Client ID ou Client Secret inválidos."
                    ));
        }

        String token = tokenService.gerarToken(clientId);
        return ResponseEntity.ok(Map.of(
            "access_token", token,
            "token_type", "Bearer",
            "expires_in", 7200
        ));
    }
}
