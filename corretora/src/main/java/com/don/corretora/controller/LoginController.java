package com.don.corretora.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.don.corretora.model.Cliente;
import com.don.corretora.model.ClienteDto;
import com.don.corretora.repository.ClienteRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@Service
public class LoginController {

    @Autowired
    private ClienteRepository clienteRepository;

    PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String getSignupPage() {
        return "home/login";
    }

    @PostMapping("/login")
    public String submitLogin(ClienteDto clienteDto, HttpServletRequest request) {
        Optional<Cliente> clienteOptional = clienteRepository.findByNome(clienteDto.getNome());

        if (clienteOptional.isPresent()) {
            Cliente cliente = clienteOptional.get();

            if (passwordEncoder.matches(clienteDto.getSenha(), cliente.getSenha())) {

                HttpSession session = request.getSession();
                session.setAttribute("clienteLogado", cliente);

                return "redirect:/home";

            } else {
                System.out.println("Login nao existe ou Login Incorreto");
                return "redirect:/backoffice/login?error";
            }
        }
        return "redirect:/backoffice/login?error";
    }
}
