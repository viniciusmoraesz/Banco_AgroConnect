package br.org.agroconnect.models;
import java.util.UUID;

public class Agricultor {
    private String tpCultivo;
    private UUID cdUsuario;

    public Agricultor() {}

    public Agricultor(String tpCultivo, UUID cdUsuario) {
        this.tpCultivo = tpCultivo;
        this.cdUsuario = cdUsuario;
    }

    public Agricultor(UUID cdUsuario) {
        this.cdUsuario = cdUsuario;
    }

    public String getTpCultivo() { return tpCultivo; }
    public void setTpCultivo(String tpCultivo) { this.tpCultivo = tpCultivo; }

    public UUID getCdUsuario() { return cdUsuario; }
    public void setCdUsuario(UUID cdUsuario) { this.cdUsuario = cdUsuario; }
}


