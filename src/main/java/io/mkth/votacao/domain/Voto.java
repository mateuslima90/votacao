package io.mkth.votacao.domain;

import java.util.List;

public class Voto {

    private String id;
    private String idEvento;
    private List<String> candidatos;
    private String idUsuario;
    private String meuVoto;

    public Voto() {

    }

    public Voto(String id, String idEvento, List<String> candidatos, String idUsuario, String meuVoto) {
        this.id = id;
        this.idEvento = idEvento;
        this.candidatos = candidatos;
        this.idUsuario = idUsuario;
        this.meuVoto = meuVoto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(String idEvento) {
        this.idEvento = idEvento;
    }

    public List<String> getCandidatos() {
        return candidatos;
    }

    public void setCandidatos(List<String> candidatos) {
        this.candidatos = candidatos;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getMeuVoto() {
        return meuVoto;
    }

    public void setMeuVoto(String meuVoto) {
        this.meuVoto = meuVoto;
    }

    @Override
    public String toString() {
        return "Voto{" +
                "id='" + id + '\'' +
                ", idEvento='" + idEvento + '\'' +
                ", candidatos=" + candidatos +
                ", idUsuario='" + idUsuario + '\'' +
                ", meuVoto='" + meuVoto + '\'' +
                '}';
    }
}
