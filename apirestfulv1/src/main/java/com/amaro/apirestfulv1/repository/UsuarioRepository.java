package com.amaro.apirestfulv1.repository;

import com.amaro.apirestfulv1.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByContaAndSenha(String conta, String senha);
}
