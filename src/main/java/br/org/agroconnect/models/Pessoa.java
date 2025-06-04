package br.org.agroconnect.models;
import java.util.UUID;

public class Pessoa {
    private String nmNome;
    private String nrCpf;
    private UUID cdUsuario;
    private String tpUsuario;

    public Pessoa() {}

    public Pessoa(String nmNome, String nrCpf, UUID cdUsuario, String tpUsuario) {
        this.nmNome = nmNome;
        this.nrCpf = nrCpf;
        this.cdUsuario = cdUsuario;
        this.tpUsuario = tpUsuario;
    }

    public Pessoa(String nmNome, String nrCpf, String tpUsuario) {
        this.nmNome = nmNome;
        this.nrCpf = nrCpf;
        this.tpUsuario = tpUsuario;
    }

    public String getNmNome() { return nmNome; }
    public void setNmNome(String nmNome) { this.nmNome = nmNome; }

    public String getNrCpf() { return nrCpf; }
    public void setNrCpf(String nrCpf) { this.nrCpf = nrCpf; }

    public UUID getCdUsuario() { return cdUsuario; }
    public void setCdUsuario(UUID cdUsuario) { this.cdUsuario = cdUsuario; }

    public String getTpUsuario() { return tpUsuario; }
    public void setTpUsuario(String tpUsuario) { this.tpUsuario = tpUsuario; }
}
