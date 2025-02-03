package com.don.don.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.don.don.model.Cliente;
import com.don.don.model.ClienteDto;
import com.don.don.repository.ClienteRepository;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.validation.Valid;

@Controller
@Service
public class CriaContaController {

    @Autowired
    private ClienteRepository clienteRepository;

    PasswordEncoder passwordEncoder;

    public CriaContaController(ClienteRepository usuarioRepository) {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @GetMapping("/cadastrar")
    public String getCadastrar(Model model) {
        ClienteDto clienteDto = new ClienteDto();
        model.addAttribute("clienteDto", clienteDto);
        return "cadastro/usuario";
    }

    @PostMapping("/cadastrar")
    public String cadastrarUsuario(@ModelAttribute("clienteDto") @Valid ClienteDto clienteDto,
            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "cadastro/usuario";
        }

        if (clienteRepository.existsByNome((clienteDto.getNome()))) {
            bindingResult.rejectValue("nome", "error.clienteDto", "Este nome já está em uso");
            return "cadastro/usuario";

        }

        Cliente usuario = new Cliente();

        usuario.setNome(clienteDto.getNome());

        String senhaCriptada = this.passwordEncoder.encode(clienteDto.getSenha());

        usuario.setSenha(senhaCriptada);
        clienteRepository.save(usuario);

        return "redirect:/home";
    }
}
