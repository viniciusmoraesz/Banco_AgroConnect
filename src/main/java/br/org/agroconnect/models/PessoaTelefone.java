package br.org.agroconnect.models;
import java.util.UUID;


public class PessoaTelefone {
    private int nrDdd;
    private int nrTelefone;
    private UUID cdUsuario;

    public PessoaTelefone() {}

    public PessoaTelefone(int nrDdd, int nrTelefone, UUID cdUsuario) {
        this.nrDdd = nrDdd;
        this.nrTelefone = nrTelefone;
        this.cdUsuario = cdUsuario;
    }

    public PessoaTelefone(int nrDdd, int nrTelefone) {
        this.nrDdd = nrDdd;
        this.nrTelefone = nrTelefone;
    }

    public int getNrDdd() { return nrDdd; }
    public void setNrDdd(int nrDdd) { this.nrDdd = nrDdd; }

    public int getNrTelefone() { return nrTelefone; }
    public void setNrTelefone(int nrTelefone) { this.nrTelefone = nrTelefone; }

    public UUID getCdUsuario() { return cdUsuario; }
    public void setCdUsuario(UUID cdUsuario) { this.cdUsuario = cdUsuario; }
}

