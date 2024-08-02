package com.don.corretora.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.don.corretora.repository.ProdutoRepository;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/produto")
public class ProdutoController {

    ProdutoRepository produtoRepository;


    @GetMapping("/view/{id}")
    public String mostraProduto(@PathVariable int id, Model model, HttpServletRequest request){
        
    }

    
}
