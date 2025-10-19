package com.amaro.apirestfulv1.controller;


import com.amaro.apirestfulv1.model.Usuario;
import com.amaro.apirestfulv1.service.AutenticacaoService;
import com.amaro.apirestfulv1.util.TokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"http://192.168.15.15:5173", "http://localhost:5173", "http://127.0.0.1:5173", "https://vercel-message-app-front-end.vercel.app/"})
@RequestMapping("autenticacao") //http://localhost:8080/autenticacao
public class AutenticacaoController {

    @Autowired
    private AutenticacaoService autenticacaoService;

    @PostMapping("login") //http://localhost:8080/autenticacao/login
    public TokenResponse login(@RequestBody Usuario usuario) {
        Usuario usuarioLogado = autenticacaoService.login(usuario);
        if (usuarioLogado != null) {
            return new TokenResponse(usuarioLogado.getId());
        } else {
            return new TokenResponse(0);
        }
    }
}
