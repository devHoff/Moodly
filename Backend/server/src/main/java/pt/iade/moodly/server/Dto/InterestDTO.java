package pt.iade.moodly.server.Dto;

public class InterestDTO {
    private String tipo;  // "musica" | "filme" | "jogo"
    private String nome;

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
}

