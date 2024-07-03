package com.don.corretora.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.don.corretora.model.Usuario;
import com.don.corretora.model.UsuarioDto;
import com.don.corretora.repository.UsuarioRepository;

import jakarta.validation.Valid;

@Controller
@Service
public class CriaContaController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    PasswordEncoder passwordEncoder;
    

    public CriaContaController(UsuarioRepository usuarioRepository) {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

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


        if(usuarioRepository.existsByNome((usuarioDto.getNome()))){
            bindingResult.rejectValue("nome", "error.usuarioDto", "Este nome já está em uso");
            return "cadastro/usuario";
            
        }

        Usuario usuario = new Usuario();

        usuario.setNome(usuarioDto.getNome());

        String senhaCriptada = this.passwordEncoder.encode(usuarioDto.getSenha());

        usuario.setSenha(senhaCriptada);
        usuarioRepository.save(usuario);

        return "redirect:/home";
    }
}
