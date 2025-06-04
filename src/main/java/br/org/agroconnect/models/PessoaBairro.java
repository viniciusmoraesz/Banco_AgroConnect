package br.org.agroconnect.models;
import java.util.UUID;

public class PessoaBairro {
    private UUID cdBairro;
    private String nmBairro;
    private UUID cdCidade;

    public PessoaBairro() {}

    public PessoaBairro(String nmBairro, UUID cdCidade) {
        this.nmBairro = nmBairro;
        this.cdCidade = cdCidade;
    }

    public PessoaBairro(UUID cdBairro, String nmBairro, UUID cdCidade) {
        this.cdBairro = cdBairro;
        this.nmBairro = nmBairro;
        this.cdCidade = cdCidade;
    }

    public UUID getCdBairro() { return cdBairro; }
    public void setCdBairro(UUID cdBairro) { this.cdBairro = cdBairro; }

    public String getNmBairro() { return nmBairro; }
    public void setNmBairro(String nmBairro) { this.nmBairro = nmBairro; }

    public UUID getCdCidade() { return cdCidade; }
    public void setCdCidade(UUID cdCidade) { this.cdCidade = cdCidade; }
}
