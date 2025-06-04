package br.org.agroconnect.models;
import java.util.UUID;

public class PessoaCidade {
    private UUID cdCidade;
    private String nmCidade;
    private UUID cdEstado;

    public PessoaCidade() {}

    public PessoaCidade(String nmCidade, UUID cdEstado) {
        this.nmCidade = nmCidade;
        this.cdEstado = cdEstado;
    }

    public PessoaCidade(UUID cdCidade, String nmCidade, UUID cdEstado) {
        this.cdCidade = cdCidade;
        this.nmCidade = nmCidade;
        this.cdEstado = cdEstado;
    }

    public UUID getCdCidade() { return cdCidade; }
    public void setCdCidade(UUID cdCidade) { this.cdCidade = cdCidade; }

    public String getNmCidade() { return nmCidade; }
    public void setNmCidade(String nmCidade) { this.nmCidade = nmCidade; }

    public UUID getCdEstado() { return cdEstado; }
    public void setCdEstado(UUID cdEstado) { this.cdEstado = cdEstado; }
}
