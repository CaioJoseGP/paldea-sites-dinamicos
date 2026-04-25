package com.jardim.paldea.service;

import com.jardim.paldea.model.LoginForm;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    public static final String STAFF_EMAIL = "equipe@paldea.com";
    public static final String STAFF_PASSWORD = "paldea123";

    public ServiceResult<String> authenticate(LoginForm loginForm) {
        String email = safe(loginForm.getEmail());
        String password = safe(loginForm.getSenha());

        if (email.isBlank() || password.isBlank()) {
            return ServiceResult.badRequest("Informe e-mail e senha para acessar a area da loja.");
        }

        if (!STAFF_EMAIL.equalsIgnoreCase(email) || !STAFF_PASSWORD.equals(password)) {
            return ServiceResult.badRequest("As credenciais informadas nao conferem com o acesso da equipe.");
        }

        String displayName = email.substring(0, email.indexOf('@'));
        displayName = displayName.substring(0, 1).toUpperCase() + displayName.substring(1);
        return ServiceResult.success("Bem-vindo, " + displayName + ". O painel comercial da Paldea esta disponivel.", displayName);
    }

    private String safe(String value) {
        return value == null ? "" : value.trim();
    }
}
