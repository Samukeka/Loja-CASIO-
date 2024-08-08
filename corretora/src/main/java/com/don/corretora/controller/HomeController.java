package com.don.corretora.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.don.corretora.model.Banner;
import com.don.corretora.model.Produto;
import com.don.corretora.model.Usuario;
import com.don.corretora.repository.BannerRepository;
import com.don.corretora.repository.ProdutoRepository;

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
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");

        if (usuarioLogado != null) {
            model.addAttribute("usuarioLogado", true);
            model.addAttribute("usuarioId", usuarioLogado.getId());
            model.addAttribute("nomeUsuario", usuarioLogado.getNome());

        } else {
            model.addAttribute("usuarioLogado", false);
        }

        List<Banner> banners = bannerRepository.findAll();
        model.addAttribute("banners", banners);
        List<Produto> produtos = produtoRepository.findAll();
        model.addAttribute("destaques", produtoRepository.findByDestaqueTrue());
        model.addAttribute("novidades", produtoRepository.findByNovidadeTrue());
        model.addAttribute("descontos", produtoRepository.findByDescontoTrue());
        model.addAttribute("maisVendidos", produtoRepository.findByMaisVendidoTrue());
        model.addAttribute("produtos", produtos);

        return "home/index";
    }

}
