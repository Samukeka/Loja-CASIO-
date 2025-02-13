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
import com.don.don.model.Endereco;
import com.don.don.repository.ClienteRepository;
import com.don.don.repository.EnderecoRepository;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.validation.Valid;

@Controller
@Service
public class CriaContaController {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

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

        if (!clienteDto.getSenha().equals(clienteDto.getConfirmaSenha())) {
            bindingResult.rejectValue("confirmaSenha", "error.clienteDto", "As senhas não coincidem");
            return "cadastro/usuario";

        }

        Cliente cliente = new Cliente();
        cliente.setNome(clienteDto.getNome());
        cliente.setEmail(clienteDto.getEmail());
        cliente.setGenero(clienteDto.getGenero());
        cliente.setDataNascimento(clienteDto.getDataNascimento());

        String senhaCriptada = this.passwordEncoder.encode(clienteDto.getSenha());
        cliente.setSenha(senhaCriptada);

        Endereco endereco = new Endereco();
        endereco.setCep(clienteDto.getCep());
        endereco.setLogradouro(clienteDto.getLogradouro());
        endereco.setNumero(clienteDto.getNumero());
        endereco.setComplemento(clienteDto.getComplemento());
        endereco.setBairro(clienteDto.getBairro());
        endereco.setCidade(clienteDto.getCidade());
        endereco.setUf(clienteDto.getUf());

        enderecoRepository.save(endereco);
        cliente.setEnderecoPadrao(endereco);

        clienteRepository.save(cliente);

        return "redirect:/index";
    }
}
