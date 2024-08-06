package com.don.corretora.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.don.corretora.model.Produto;
import com.don.corretora.model.ProdutoDto;
import com.don.corretora.model.Usuario;
import com.don.corretora.repository.ProdutoRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    ProdutoRepository produtoRepository;

    @GetMapping("/view/{id}")
    public String mostraProduto(@PathVariable Long id, Model model, HttpServletRequest request) {

        try {
            Produto produto = produtoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Produto Não Encontrado"));
            HttpSession session = request.getSession();
            Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");

            if (usuarioLogado != null) {
                model.addAttribute("usuarioLogado", true);
                model.addAttribute("usuarioId", usuarioLogado.getId());
                model.addAttribute("nomeUsuario", usuarioLogado.getNome());
            } else {
                model.addAttribute("usuarioLogado", false);
            }

            ProdutoDto produtoDto = new ProdutoDto();
            produtoDto.setNome(produto.getNome());
            produtoDto.setPreco(produto.getPreco());
            produtoDto.setMarca(produto.getMarca());
            produtoDto.setSerie(produto.getSerie());
            produtoDto.setColecao(produto.getColecao());
            produtoDto.setCor(produto.getCor());
            produtoDto.setEstilo(produto.getEstilo());
            produtoDto.setDescricao(produto.getDescricao());

            model.addAttribute("produto", produto);
            model.addAttribute("produtoDto", produtoDto);

            List<String> imagens = produto.getImagens();
            List<Produto> produtosMesmaSerie = produtoRepository.findBySerie(produto.getSerie())
                    .stream()
                    .filter(p -> !p.getId().equals(id))
                    .collect(Collectors.toList());
            model.addAttribute("produtosMesmaSerie", produtosMesmaSerie);

            System.out.println(imagens);
            model.addAttribute("imagens", imagens);
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
            return "redirect:/home";
        }

        return "visualizar/index";

    }

}
