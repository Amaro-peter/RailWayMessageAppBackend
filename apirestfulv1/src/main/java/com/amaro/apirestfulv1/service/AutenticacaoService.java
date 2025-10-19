package com.amaro.apirestfulv1.service;

import com.amaro.apirestfulv1.model.Usuario;
import com.amaro.apirestfulv1.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario login(Usuario usuario) {
        return usuarioRepository.findByContaAndSenha(usuario.getConta(), usuario.getSenha());
    }
}
