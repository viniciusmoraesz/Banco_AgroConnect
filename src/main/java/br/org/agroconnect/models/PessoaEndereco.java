package br.org.agroconnect.models;
import java.util.UUID;


public class PessoaEndereco {
    private String nrCep;
    private int nrEndereco;
    private String nmEndereco;
    private UUID cdBairro;
    private UUID cdUsuario;

    public PessoaEndereco() {}

    public PessoaEndereco(String nrCep, int nrEndereco, String nmEndereco, UUID cdBairro, UUID cdUsuario) {
        this.nrCep = nrCep;
        this.nrEndereco = nrEndereco;
        this.nmEndereco = nmEndereco;
        this.cdBairro = cdBairro;
        this.cdUsuario = cdUsuario;
    }

    public PessoaEndereco(String nrCep, int nrEndereco, String nmEndereco, UUID cdBairro) {
        this.nrCep = nrCep;
        this.nrEndereco = nrEndereco;
        this.nmEndereco = nmEndereco;
        this.cdBairro = cdBairro;
    }

    public String getNrCep() { return nrCep; }
    public void setNrCep(String nrCep) { this.nrCep = nrCep; }

    public int getNrEndereco() { return nrEndereco; }
    public void setNrEndereco(int nrEndereco) { this.nrEndereco = nrEndereco; }

    public String getNmEndereco() { return nmEndereco; }
    public void setNmEndereco(String nmEndereco) { this.nmEndereco = nmEndereco; }

    public UUID getCdBairro() { return cdBairro; }
    public void setCdBairro(UUID cdBairro) { this.cdBairro = cdBairro; }

    public UUID getCdUsuario() { return cdUsuario; }
    public void setCdUsuario(UUID cdUsuario) { this.cdUsuario = cdUsuario; }
}