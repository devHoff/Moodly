package pt.iade.moodly.server.Dto;

import java.util.List;

public class UserProfileUpdateRequest {
    private String fotoPerfil;
    private List<InterestDTO> interesses; 

    public String getFotoPerfil() { return fotoPerfil; }
    public void setFotoPerfil(String fotoPerfil) { this.fotoPerfil = fotoPerfil; }
    public List<InterestDTO> getInteresses() { return interesses; }
    public void setInteresses(List<InterestDTO> interesses) { this.interesses = interesses; }
}


