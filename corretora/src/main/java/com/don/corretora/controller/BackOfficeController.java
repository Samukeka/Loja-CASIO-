package com.don.corretora.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.don.corretora.model.Funcionario;
import com.don.corretora.model.FuncionarioDto;
import com.don.corretora.repository.FuncionarioRepository;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@Service
@RequestMapping("/backoffice")
public class BackOfficeController {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    PasswordEncoder passwordEncoder;


    @GetMapping("/login")
    public String getPageLogin() {
        return "backoffice/login";
    }

    @GetMapping("/home")
    public String getPageHome() {
        return "backoffice/home";
    }

    @PostMapping("/login")
    public String fazLogin(FuncionarioDto funcionarioDto, HttpServletRequest request){
        Optional<Funcionario> funcionarOptional = funcionarioRepository.findByEmail(funcionarioDto.getEmail());

        if(funcionarOptional.isPresent()){
            Funcionario funcionario = funcionarOptional.get();

            if(passwordEncoder.matches(funcionarioDto.getSenha(), funcionario.getSenha())){

                HttpSession session = request.getSession();
                session.setAttribute("funcionarioLogado", funcionario);

                return "redirect:/backoffice/home";
            }
            else{
                System.out.println("erro de login");
                return "redirect:/login?error";
            }
        }
        return "redirect:/login?error";
    }
    
}
