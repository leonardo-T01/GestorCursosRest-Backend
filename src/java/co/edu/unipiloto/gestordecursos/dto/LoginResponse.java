package co.edu.unipiloto.gestordecursos.dto;

import co.edu.unipiloto.gestordecursos.entidades.Usuario;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement // <-- Esto le dice a GlassFish cómo convertir a JSON
public class LoginResponse {
    private String token;
    private Usuario usuario;

    // Constructor vacío (Obligatorio para el conversor de GlassFish)
    public LoginResponse() {
    }

    public LoginResponse(String token, Usuario usuario) {
        this.token = token;
        this.usuario = usuario;
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
}