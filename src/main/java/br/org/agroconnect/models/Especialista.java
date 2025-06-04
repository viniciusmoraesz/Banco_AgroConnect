package br.org.agroconnect.models;
import java.util.UUID;

public class Especialista {
    private String especialidade;
    private UUID cdUsuario;

    public Especialista() {}

    public Especialista(String especialidade, UUID cdUsuario) {
        this.especialidade = especialidade;
        this.cdUsuario = cdUsuario;
    }

    public Especialista(UUID cdUsuario) {
        this.cdUsuario = cdUsuario;
    }

    public String getEspecialidade() { return especialidade; }
    public void setEspecialidade(String especialidade) { this.especialidade = especialidade; }

    public UUID getCdUsuario() { return cdUsuario; }
    public void setCdUsuario(UUID cdUsuario) { this.cdUsuario = cdUsuario; }
}
