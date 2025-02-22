package com.don.don.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.don.don.model.Banner;
import com.don.don.model.Cliente;
import com.don.don.model.Produto;
import com.don.don.repository.BannerRepository;
import com.don.don.repository.ProdutoRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private BannerRepository bannerRepository;

    @GetMapping({ "", "/" })
    public String home(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        Cliente clienteLogado = (Cliente) session.getAttribute("clienteLogado");

        if (clienteLogado != null) {
            model.addAttribute("clienteLogado", true);
            model.addAttribute("clienteI+d", clienteLogado.getId());
            model.addAttribute("nomeCliente", clienteLogado.getNome());

        } else {
            model.addAttribute("clienteLogado", false);
        }

        List<Banner> banners = bannerRepository.findAll();
        model.addAttribute("banners", banners);
        List<Produto> produtos = produtoRepository.findAll();
        model.addAttribute("destaques", produtoRepository.findByDestaqueTrue());
        model.addAttribute("novidades", produtoRepository.findByNovidadeTrue());
        model.addAttribute("descontos", produtoRepository.findByDescontoTrue());
        model.addAttribute("maisVendido", produtoRepository.findByMaisVendidoTrue());
        model.addAttribute("produtos", produtos);

        return "home/index";
    }

}
