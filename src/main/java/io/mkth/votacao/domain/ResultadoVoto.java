package io.mkth.votacao.domain;

public class ResultadoVoto {

    private String usuario;
    private String voto;

    public ResultadoVoto(String usuario, String voto) {
        this.usuario = usuario;
        this.voto = voto;
    }

    public ResultadoVoto() {}

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getVoto() {
        return voto;
    }

    public void setVoto(String voto) {
        this.voto = voto;
    }
}
