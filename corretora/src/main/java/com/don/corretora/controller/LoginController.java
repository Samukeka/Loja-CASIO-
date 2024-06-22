package com.don.corretora.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.don.corretora.model.Usuario;
import com.don.corretora.model.UsuarioDto;
import com.don.corretora.repository.UsuarioRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@Service
public class LoginController {
    

    @Autowired
    private UsuarioRepository usuarioRepository;


     @GetMapping("/login")
    public String getSignupPage() {
        return "home/login";
    }

    @PostMapping("/login")
    public String submitLogin(UsuarioDto usuarioDto, HttpServletRequest request) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByNome(usuarioDto.getNome());

        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();


            if (usuarioDto.getSenha().matches(usuario.getSenha())) {

                HttpSession session = request.getSession();
                session.setAttribute("usuarioLogado", usuario);

                return "redirect:/home";

            } else {
                System.out.println("Login nao existe ou Login Incorreto");
                return "redirect:/login?error";
            }
        }
        return "redirect:/login?error";
    }
}
