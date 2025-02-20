package com.don.don.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.don.don.model.Cliente;
import com.don.don.model.ItemCarrinho;
import com.don.don.model.ItemPedido;
import com.don.don.model.Pedido;
import com.don.don.model.StatusPedido;
import com.don.don.repository.PedidoRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class CheckoutController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @GetMapping("/checkout")
    public String getCheckout(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();

        List<ItemCarrinho> carrinho = (List<ItemCarrinho>) session.getAttribute("carrinho");

        if (carrinho == null || carrinho.isEmpty()) {
            return "redirect:/carrinho";
        }

        BigDecimal total = carrinho.stream()
                .map(item -> item.getProduto().getPreco().multiply(BigDecimal.valueOf(item.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal frete = new BigDecimal("15.00");

        model.addAttribute("carrinho", carrinho);
        model.addAttribute("total", total);
        model.addAttribute("frete", frete);
        return "checkout/index";
    }

    @PostMapping("/finalizar")
    public String finalizarCompra(HttpServletRequest request) {

        HttpSession session = request.getSession();
        List<ItemCarrinho> carrinho = (List<ItemCarrinho>) session.getAttribute("carrinho");

        if (carrinho == null || carrinho.isEmpty()) {
            return "redirect:/carrinho";
        }

        Cliente cliente = (Cliente) session.getAttribute("cliente");
        if (cliente == null) {
            return "redirect:/login";
        }

        Pedido pedido = new Pedido();
        pedido.setItens(carrinho.stream().map(item -> new ItemPedido(pedido, item.getProduto(), item.getQuantidade()))
                .toList());
        pedido.setTotal(calcularTotalCarrinho(carrinho));
        pedido.setFrete(new BigDecimal("15.00"));
        pedido.setMetodoPagamento("PIX");
        pedido.setStatus(StatusPedido.PROCESSANDO);
        pedido.setCliente(cliente);

        pedidoRepository.save(pedido);

        session.removeAttribute("carrinho");

        return "redirect:/pedido-sucesso";
    }

    private BigDecimal calcularTotalCarrinho(List<ItemCarrinho> carrinho) {
        return carrinho.stream()
                .map(item -> item.getProduto().getPreco().multiply(BigDecimal.valueOf(item.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
