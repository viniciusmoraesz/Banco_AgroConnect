package br.org.agroconnect.models;
import java.util.UUID;

public class Usuario {
    private UUID cdUsuario;
    private String dsEmail;
    private String dsSenha;

    public Usuario() {}

    public Usuario(String dsEmail, String dsSenha) {
        this.dsEmail = dsEmail;
        this.dsSenha = dsSenha;
    }

    public Usuario(UUID cdUsuario, String dsEmail, String dsSenha) {
        this.cdUsuario = cdUsuario;
        this.dsEmail = dsEmail;
        this.dsSenha = dsSenha;
    }

    public UUID getCdUsuario() { return cdUsuario; }
    public void setCdUsuario(UUID cdUsuario) { this.cdUsuario = cdUsuario; }

    public String getDsEmail() { return dsEmail; }
    public void setDsEmail(String dsEmail) { this.dsEmail = dsEmail; }

    public String getDsSenha() { return dsSenha; }
    public void setDsSenha(String dsSenha) { this.dsSenha = dsSenha; }
}
