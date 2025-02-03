package com.don.don.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; // Importação correta
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.don.don.model.ItemCarrinho;
import com.don.don.model.Produto;
import com.don.don.repository.ProdutoRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/carrinho")
public class CarrinhoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    public CarrinhoController(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @GetMapping
    public String visualizarCarrinho(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        List<ItemCarrinho> carrinho = recuperarCarrinho(session);

        // Calcular o total
        double total = carrinho.stream()
                .mapToDouble(item -> item.getProduto().getPreco().doubleValue() * item.getQuantidade())
                .sum();

        model.addAttribute("carrinho", carrinho);
        model.addAttribute("total", total); // Adicionar o total ao modelo
        return "carrinho/index";
    }

    @SuppressWarnings("unchecked")
    private List<ItemCarrinho> recuperarCarrinho(HttpSession session) {
        Object carrinhoObj = session.getAttribute("carrinho");

        if (carrinhoObj instanceof List<?>) {
            List<?> listaGenerica = (List<?>) carrinhoObj;

            if (!listaGenerica.isEmpty() && listaGenerica.get(0) instanceof ItemCarrinho) {
                return (List<ItemCarrinho>) listaGenerica;
            }
        }

        return new ArrayList<>();
    }

    @PostMapping("/adicionar/{id}")
    public String adicionarProdutoAoCarrinho(@PathVariable("id") Long id, HttpServletRequest request) {
        HttpSession session = request.getSession();
        List<ItemCarrinho> carrinho = recuperarCarrinho(session);

        if (carrinho == null) {
            carrinho = new ArrayList<>();
        }

        Produto produto = produtoRepository.findById(id).orElse(null);

        if (produto != null) {
            boolean encontrado = false;
            for (ItemCarrinho item : carrinho) {
                if (item.getProduto().getId().equals(id)) {
                    item.setQuantidade(item.getQuantidade() + 1);
                    encontrado = true;
                    break;
                }
            }

            if (!encontrado) {
                carrinho.add(new ItemCarrinho(produto, 1));
            }
        }

        session.setAttribute("carrinho", carrinho);
        return "redirect:/carrinho";
    }

    @PostMapping("/remover/{id}")
    public String removerProdutoDoCarrinho(@PathVariable("id") Long id, HttpServletRequest request) {
        HttpSession session = request.getSession();
        List<ItemCarrinho> carrinho = recuperarCarrinho(session);

        if (carrinho != null) {
            carrinho.removeIf(item -> item.getProduto().getId().equals(id));
        }

        session.setAttribute("carrinho", carrinho);
        return "redirect:/carrinho";
    }

    @PostMapping("/atualizar/{id}")
    public String atualizarQuantidade(@PathVariable Long id, @RequestParam("quantidade") int quantidade,
            HttpServletRequest request) {
        HttpSession session = request.getSession();
        List<ItemCarrinho> carrinho = recuperarCarrinho(session);

        if (carrinho != null) {
            for (ItemCarrinho item : carrinho) {
                if (item.getProduto().getId().equals(id)) {
                    if (quantidade < 1) { // Impede quantidade inválida
                        quantidade = 1;
                    }
                    item.setQuantidade(quantidade);
                    break;
                }
            }
            session.setAttribute("carrinho", carrinho);
        }
        return "redirect:/carrinho";
    }

}
