package br.org.agroconnect.models;
import java.util.UUID;

public class PessoaEstado {
    private UUID cdEstado;
    private String nmEstado;
    private String sgEstado;

    public PessoaEstado() {}

    public PessoaEstado(String nmEstado, String sgEstado) {
        this.nmEstado = nmEstado;
        this.sgEstado = sgEstado;
    }

    public PessoaEstado(UUID cdEstado, String nmEstado, String sgEstado) {
        this.cdEstado = cdEstado;
        this.nmEstado = nmEstado;
        this.sgEstado = sgEstado;
    }

    public UUID getCdEstado() { return cdEstado; }
    public void setCdEstado(UUID cdEstado) { this.cdEstado = cdEstado; }

    public String getNmEstado() { return nmEstado; }
    public void setNmEstado(String nmEstado) { this.nmEstado = nmEstado; }

    public String getSgEstado() { return sgEstado; }
    public void setSgEstado(String sgEstado) { this.sgEstado = sgEstado; }
}