package com.don.corretora.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.don.corretora.model.Usuario;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class HomeController {


     @GetMapping({"", "/"})
    public String home(HttpServletRequest request, Model model){
        HttpSession session = request.getSession();
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");

        
        if (usuarioLogado != null) {
            model.addAttribute("usuarioLogado", true);
            model.addAttribute("usuarioId", usuarioLogado.getId());
            model.addAttribute("nomeUsuario", usuarioLogado.getNome());

        } else {
            model.addAttribute("usuarioLogado", false);
        }

        return "home/index";
    }
    
}
