package com.don.don.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.don.don.model.Cliente;
import com.don.don.model.ClienteDto;
import com.don.don.repository.ClienteRepository;
import com.don.don.repository.EnderecoRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@Service
@RequestMapping("/cliente")

public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ClienteController(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String getLogin(Model model) {
        ClienteDto clienteDto = new ClienteDto();
        model.addAttribute("clienteDto", clienteDto);
        return "cliente/login";
    }

    @GetMapping("/perfil")
    public String getPerfil(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession();
        Cliente clienteLogado = (Cliente) session.getAttribute("clienteLogado");

        if (clienteLogado != null) {
            model.addAttribute("clienteLogado", true);
            model.addAttribute("clienteId", clienteLogado.getId());
            model.addAttribute("nomeCliente", clienteLogado.getNome());

        } else {
            model.addAttribute("clienteLogado", false);
        }

        return "cliente/perfil";
    }

    @PostMapping("/login")
    public String submitLogin(ClienteDto clienteDto, HttpServletRequest request) {
        Optional<Cliente> clienteOptional = clienteRepository.findByEmail(clienteDto.getEmail());

        if (clienteOptional.isPresent()) {
            Cliente cliente = clienteOptional.get();

            if (passwordEncoder.matches(clienteDto.getSenha(), cliente.getSenha())) {

                HttpSession session = request.getSession();
                session.setAttribute("clienteLogado", cliente);

                return "redirect:/";

            } else {
                System.out.println("Login nao existe ou Login Incorreto");
                return "redirect:/cliente/login?error";
            }
        }
        return "redirect:/cliente/login?error";
    }

}
