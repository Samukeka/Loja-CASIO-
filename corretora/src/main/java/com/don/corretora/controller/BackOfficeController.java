package com.don.corretora.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.don.corretora.model.Funcionario;
import com.don.corretora.model.FuncionarioDto;
import com.don.corretora.model.Usuario;
import com.don.corretora.repository.FuncionarioRepository;
import com.don.corretora.repository.UsuarioRepository;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@Service
@RequestMapping("/backoffice")
public class BackOfficeController {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    PasswordEncoder passwordEncoder;

    public BackOfficeController(FuncionarioRepository funcionarioRepository) {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @GetMapping("/login")
    public String getPageLogin() {
        return "backoffice/login";
    }

    @GetMapping("/home")
    public String getPageHome(HttpSession session, Model model) {
        List<Funcionario> funcionarios = funcionarioRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));

        String cargoUsuario = (String) session.getAttribute("cargo");


        if (session == null || cargoUsuario == null) {
            return "redirect:/backoffice/login";
        }
        model.addAttribute("cargoUsuario", cargoUsuario);
        model.addAttribute("funcionarios", funcionarios);

        
        return "backoffice/home";
    }

    @PostMapping("/login")
    public String fazLogin(FuncionarioDto funcionarioDto, HttpServletRequest request){
        Optional<Funcionario> funcionarOptional = funcionarioRepository.findByEmail(funcionarioDto.getEmail());


        String senhaEncriptada = passwordEncoder.encode(funcionarioDto.getSenha());

        if(funcionarOptional.isPresent()){
            Funcionario funcionario = funcionarOptional.get();

            if(passwordEncoder.matches(funcionarioDto.getSenha(), funcionario.getSenha())){

                HttpSession session = request.getSession();
                session.setAttribute("funcionarioLogado", funcionario);
                session.setAttribute("cargo", funcionario.getCargo());

                if("ATIVO".equals(funcionario.getStatus())){
                    return "redirect:/backoffice/home";
                }
                else{
                    return "redirect:/backoffice/login?error";
                }
            }
            else{
                System.out.println("erro de login");
                return "redirect:/backoffice/login?error";
            }
        }
        return "redirect:/backoffice/login?error";
    }

    @GetMapping("/cadastrar")
    public String getCadastrar(Model model, HttpSession session){

        String cargoUsuario = (String) session.getAttribute("cargo");


        if (session == null || cargoUsuario == null) {
            return "redirect:/backoffice/login";
        }

        model.addAttribute("cargoUsuario", cargoUsuario);
        FuncionarioDto funcionarioDto = new FuncionarioDto();
        model.addAttribute("funcionarioDto", funcionarioDto);

        return "backoffice/cadastro";

        
    }

    @PostMapping("/cadastrar")
    public String cadastrarFuncionario(@ModelAttribute("funcionarioDto") @Valid FuncionarioDto funcionarioDto, BindingResult bindingResult, Model model){

        if(bindingResult.hasErrors()){
            return "backoffice/cadastro";
        }

        if(funcionarioRepository.existsByEmail((funcionarioDto.getEmail()))){

            bindingResult.rejectValue("email", "error.funcionarioDto", "Este email ja esta em uso");
            return "backoffice/cadastro";
        }

        Funcionario funcionario = new Funcionario();
        String senhaCriptada = this.passwordEncoder.encode(funcionarioDto.getSenha());

        funcionario.setNome(funcionarioDto.getNome());
        funcionario.setEmail(funcionarioDto.getEmail());
        funcionario.setSenha(senhaCriptada);

        funcionario.setCargo(funcionarioDto.getCargo());
        funcionarioRepository.save(funcionario);

        return "redirect:/backoffice/home";
    }

    @GetMapping("/editar")
    public String funcionarioInformacoes(Model model, @RequestParam Long id, HttpSession session){

        String cargoUsuario = (String) session.getAttribute("cargo");


        if (session == null || cargoUsuario == null) {
            return "redirect:/backoffice/login";
        }

        model.addAttribute("cargoUsuario", cargoUsuario);

        try{
            Funcionario funcionario = funcionarioRepository.findById(id).get();
            model.addAttribute("funcionario", funcionario);

            FuncionarioDto funcionarioDto = new FuncionarioDto();
            funcionarioDto.setNome(funcionario.getNome());
            funcionarioDto.setEmail(funcionario.getEmail());
            funcionarioDto.setSenha(funcionario.getSenha());
            funcionarioDto.setCargo(funcionario.getCargo());

            model.addAttribute("funcionarioDto",funcionarioDto);
        }
        catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
            return "redirect:/home";
        }
        return "backoffice/edita";
    }

    @PostMapping("/editar")
    public String editaFuncionario(Model model, Principal principal, @RequestParam Long id, @Valid @ModelAttribute FuncionarioDto funcionarioDto, BindingResult bindingResult, HttpSession session){

        String cargoUsuario = (String) session.getAttribute("cargo");


        if (session == null || cargoUsuario == null) {
            return "redirect:/backoffice/login";
        }

        model.addAttribute("cargoUsuario", cargoUsuario);

        // Verificar se o usuário autenticado está tentando editar seu próprio perfil
        if (principal != null && principal.getName().equals(funcionarioDto.getEmail())) {
            bindingResult.rejectValue("email", "error.funcionarioDto", "Você não pode editar seu próprio perfil");
            return "backoffice/home";
        }

        try{
            Funcionario funcionario = funcionarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Funcionario nao encontrado"));
            model.addAttribute("funcionario", funcionario);

            if(bindingResult.hasErrors()){
                return "backoffice/edita";
            }

            funcionario.setNome(funcionarioDto.getNome());
            funcionario.setEmail(funcionarioDto.getEmail());
            funcionario.setCargo(funcionarioDto.getCargo());
            funcionario.setStatus(funcionarioDto.getStatus());


            String senhaEncriptada = this.passwordEncoder.encode(funcionarioDto.getSenha());
            funcionario.setSenha(senhaEncriptada);

            funcionarioRepository.save(funcionario);
        }
        catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
        return "redirect:/backoffice/home";
    }

    @PostMapping("/alteraStatus")
    public String alteraStatus(@RequestParam Long id, @ModelAttribute FuncionarioDto funcionarioDto){
        
        Funcionario funcionario = funcionarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Funcionario nao encontrado"));
         funcionario.setStatus("ATIVO".equals(funcionario.getStatus()) ? "INATIVO" : "ATIVO");
    
         funcionarioRepository.save(funcionario);

         return "redirect:/backoffice/home";
        }
    
}
