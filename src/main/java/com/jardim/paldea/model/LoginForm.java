package com.jardim.paldea.model;

public class LoginForm {

    public static final String STAFF_EMAIL = "equipe@paldea.com";
    public static final String STAFF_PASSWORD = "paldea123";

    private String email;
    private String senha;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String validateAccess() {
        String emailText = safe(email);
        String passwordText = safe(senha);

        if (emailText.isBlank() || passwordText.isBlank()) {
            return "Informe e-mail e senha para acessar a area da loja.";
        }

        if (!STAFF_EMAIL.equalsIgnoreCase(emailText) || !STAFF_PASSWORD.equals(passwordText)) {
            return "As credenciais informadas nao conferem com o acesso da equipe.";
        }

        return null;
    }

    public String displayName() {
        String emailText = safe(email);
        String name = emailText.substring(0, emailText.indexOf('@'));
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    private String safe(String value) {
        return value == null ? "" : value.trim();
    }
}
