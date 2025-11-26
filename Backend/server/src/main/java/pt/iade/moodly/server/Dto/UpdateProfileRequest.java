package pt.iade.moodly.server.Dto;

import java.util.List;

public class UpdateProfileRequest {

    private String fotoPerfil;
    private List<InteresseDto> interesses;

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public List<InteresseDto> getInteresses() {
        return interesses;
    }

    public void setInteresses(List<InteresseDto> interesses) {
        this.interesses = interesses;
    }

    // ---------- DTO INTERNO PARA CADA INTERESSE ----------

    public static class InteresseDto {
        private String tipo; // "musica" | "filme" | "jogo" | "series" ...
        private String nome; // "Drake", "Interstellar", etc.

        public String getTipo() {
            return tipo;
        }

        public void setTipo(String tipo) {
            this.tipo = tipo;
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }
    }
}

