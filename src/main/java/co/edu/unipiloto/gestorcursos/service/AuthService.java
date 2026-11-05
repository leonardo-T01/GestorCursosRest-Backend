package co.edu.unipiloto.gestorcursos.service;

import co.edu.unipiloto.gestorcursos.config.JwtUtil;
import co.edu.unipiloto.gestorcursos.dto.AuthResponse;
import co.edu.unipiloto.gestorcursos.dto.LoginRequest;
import co.edu.unipiloto.gestorcursos.model.Usuario;
import co.edu.unipiloto.gestorcursos.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    public Usuario registrar(Usuario usuario) {
        if (usuarioRepository.existsByCorreo(usuario.getCorreo())) {
            throw new RuntimeException("El correo ya está registrado");
        }
        
        // 1. Encriptamos la contraseña
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        
        // 2. Reglas de negocio (según tu app Android)
        if ("PROFESOR".equals(usuario.getRol())) {
            usuario.setEstadoAprobacion("PENDIENTE");
        } else {
            usuario.setEstadoAprobacion("APROBADO");
        }
        usuario.setEsVerificado(true); // Lo ponemos true para saltar la verificación de correo por ahora

        return usuarioRepository.save(usuario);
    }

        public AuthResponse login(LoginRequest request) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getCorreo(), request.getPassword())
            );
        } catch (Exception e) {
            throw new Exception("Credenciales incorrectas");
        }

        Usuario usuario = usuarioRepository.findByCorreo(request.getCorreo()).get();

        // NUEVO: Regla de negocio. Bloquear si es profesor y está pendiente
        if ("PROFESOR".equals(usuario.getRol()) && "PENDIENTE".equals(usuario.getEstadoAprobacion())) {
            throw new Exception("PROFESOR_PENDIENTE");
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getCorreo());
        final String jwt = jwtUtil.generarToken(userDetails);

        return new AuthResponse(jwt, usuario);
    }
}