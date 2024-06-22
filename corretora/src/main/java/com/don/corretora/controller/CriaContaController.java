package com.don.corretora.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.don.corretora.model.Usuario;
import com.don.corretora.model.UsuarioDto;
import com.don.corretora.repository.UsuarioRepository;

import jakarta.validation.Valid;

@Controller
@Service
public class CriaContaController {

    @Autowired
    private UsuarioRepository usuarioRepository;
    

    @GetMapping("/cadastrar")
    public String getCadastrar(Model model){
        UsuarioDto usuarioDto = new UsuarioDto();
        model.addAttribute("usuarioDto", usuarioDto);
        return "cadastro/usuario";
    }


    @PostMapping("/cadastrar")
    public String cadastrarUsuario(@ModelAttribute("usuarioDto") @Valid UsuarioDto usuarioDto, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            return "cadastro/usuario";
        }

        Usuario usuario = new Usuario();

        usuario.setNome(usuarioDto.getNome());
        usuario.setSenha(usuarioDto.getSenha());

        usuarioRepository.save(usuario);

        return "redirect:/home";
    }
}
