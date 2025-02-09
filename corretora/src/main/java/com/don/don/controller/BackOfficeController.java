package com.don.don.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
import org.springframework.web.multipart.MultipartFile;

import com.don.don.model.Banner;
import com.don.don.model.BannerDTO;
import com.don.don.model.Funcionario;
import com.don.don.model.FuncionarioDto;
import com.don.don.model.Produto;
import com.don.don.model.ProdutoDto;
import com.don.don.repository.BannerRepository;
import com.don.don.repository.FuncionarioRepository;
import com.don.don.repository.ProdutoRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@Service
@RequestMapping("/backoffice")
public class BackOfficeController {

    @Autowired
    private FuncionarioRepository funcionarioRepository;
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private BannerRepository bannerRepository;

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
    public String fazLogin(FuncionarioDto funcionarioDto, HttpServletRequest request) {
        Optional<Funcionario> funcionarOptional = funcionarioRepository.findByEmail(funcionarioDto.getEmail());

        String senhaEncriptada = passwordEncoder.encode(funcionarioDto.getSenha());

        if (funcionarOptional.isPresent()) {
            Funcionario funcionario = funcionarOptional.get();

            if (passwordEncoder.matches(funcionarioDto.getSenha(), funcionario.getSenha())) {

                HttpSession session = request.getSession();
                session.setAttribute("funcionarioLogado", funcionario);
                session.setAttribute("cargo", funcionario.getCargo());

                if ("ATIVO".equals(funcionario.getStatus())) {
                    return "redirect:/backoffice/home";
                } else {
                    return "redirect:/backoffice/login?error";
                }
            } else {
                System.out.println("erro de login");
                return "redirect:/backoffice/login?error";
            }
        }
        return "redirect:/backoffice/login?error";
    }

    @GetMapping("/cadastrar")
    public String getCadastrar(Model model, HttpSession session) {

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
    public String cadastrarFuncionario(@ModelAttribute("funcionarioDto") @Valid FuncionarioDto funcionarioDto,
            BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "backoffice/cadastro";
        }

        if (funcionarioRepository.existsByEmail((funcionarioDto.getEmail()))) {

            bindingResult.rejectValue("email", "error.funcionarioDto", "Este email ja esta em uso");
            return "backoffice/cadastro";
        }

        Funcionario funcionario = new Funcionario();
        String senhaCriptada = this.passwordEncoder.encode(funcionarioDto.getSenha());

        funcionario.setNome(funcionarioDto.getNome());
        funcionario.setEmail(funcionarioDto.getEmail());
        funcionario.setSenha(senhaCriptada);
        funcionario.setStatus("ATIVO");
        funcionario.setCargo(funcionarioDto.getCargo());
        funcionarioRepository.save(funcionario);

        return "redirect:/backoffice/home";
    }

    @GetMapping("/editarFuncionario")
    public String funcionarioInformacoes(Model model, @RequestParam Long id, HttpSession session) {

        String cargoUsuario = (String) session.getAttribute("cargo");

        if (session == null || cargoUsuario == null) {
            return "redirect:/backoffice/login";
        }

        model.addAttribute("cargoUsuario", cargoUsuario);

        try {
            Funcionario funcionario = funcionarioRepository.findById(id).get();
            model.addAttribute("funcionario", funcionario);

            FuncionarioDto funcionarioDto = new FuncionarioDto();
            funcionarioDto.setNome(funcionario.getNome());
            funcionarioDto.setEmail(funcionario.getEmail());
            funcionarioDto.setSenha(funcionario.getSenha());
            funcionarioDto.setCargo(funcionario.getCargo());

            model.addAttribute("funcionarioDto", funcionarioDto);
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
            return "redirect:/home";
        }
        return "backoffice/edita";
    }

    @PostMapping("/editarFuncionario")
    public String editaFuncionario(Model model, Principal principal, @RequestParam Long id,
            @Valid @ModelAttribute FuncionarioDto funcionarioDto, BindingResult bindingResult, HttpSession session) {

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

        try {
            Funcionario funcionario = funcionarioRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Funcionario nao encontrado"));
            model.addAttribute("funcionario", funcionario);

            if (bindingResult.hasErrors()) {
                return "backoffice/edita";
            }

            funcionario.setNome(funcionarioDto.getNome());
            funcionario.setEmail(funcionarioDto.getEmail());
            funcionario.setCargo(funcionarioDto.getCargo());
            funcionario.setStatus(funcionarioDto.getStatus());

            String senhaEncriptada = this.passwordEncoder.encode(funcionarioDto.getSenha());
            funcionario.setSenha(senhaEncriptada);

            funcionarioRepository.save(funcionario);
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
        return "redirect:/backoffice/home";
    }

    @PostMapping("/alteraStatus")
    public String alteraStatus(@RequestParam Long id, @ModelAttribute FuncionarioDto funcionarioDto) {

        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Funcionario nao encontrado"));
        funcionario.setStatus("ATIVO".equals(funcionario.getStatus()) ? "INATIVO" : "ATIVO");

        funcionarioRepository.save(funcionario);

        return "redirect:/backoffice/home";
    }

    @GetMapping("/produtos")
    public String showProdutos(Model model,
            HttpSession session,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        String cargoUsuario = (String) session.getAttribute("cargo");

        if (session == null || cargoUsuario == null) {
            return "redirect:/backoffice/login";
        }

        model.addAttribute("cargoUsuario", cargoUsuario);
        Page<Produto> produtosPage = produtoRepository
                .findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")));
        model.addAttribute("produtos", produtosPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", produtosPage.getTotalPages());

        return "backoffice/produtos";

    }

    @GetMapping("/criaproduto")
    public String showCriaProdutos(Model model, HttpSession session) {

        String cargoUsuario = (String) session.getAttribute("cargo");

        if (session == null || cargoUsuario == null) {
            return "redirect:/backoffice/login";
        }
        ProdutoDto produtoDto = new ProdutoDto();
        model.addAttribute("produtoDto", produtoDto);

        return "backoffice/cadastrarProduto";
    }

    @PostMapping("/criaproduto")
    public String criaProduto(@ModelAttribute("produtoDto") @Valid ProdutoDto produtoDto, BindingResult bindingResult,
            Model model, HttpSession session) {
        String cargoUsuario = (String) session.getAttribute("cargo");

        if (session == null || cargoUsuario == null) {
            return "redirect:/backoffice/login";
        }
        if (bindingResult.hasErrors()) {
            return "backoffice/criaproduto";
        }

        if (produtoDto.getImagens() == null || produtoDto.getImagens().stream().allMatch(MultipartFile::isEmpty)) {
            bindingResult.rejectValue("imagens", "error.produto", "É necessá    rio selecionar pelo menos uma imagem.");
            return "produtos/CriaProduto";
        }

        List<String> imagensSalvas = new ArrayList<>();
        for (MultipartFile imagem : produtoDto.getImagens()) {
            String arquivo = UUID.randomUUID().toString() + "_" + imagem.getOriginalFilename();

            File diretorio = new File("src/main/resources/static/imagens_dos_produtos");

            if (!diretorio.exists()) {
                if (diretorio.mkdirs()) {
                    System.out.println("Diretório " + diretorio.getAbsolutePath() + " foi criado.");
                } else {
                    System.out.println("Falha ao criar o diretório " + diretorio.getAbsolutePath());
                }
            }

            try {
                String caminhoApp = new File("").getAbsolutePath();
                Path uploadPath = Paths.get(caminhoApp, "src/main/resources/static/imagens_dos_produtos");
                Path filePath = uploadPath.resolve(arquivo);
                Files.copy(imagem.getInputStream(), filePath);
                imagensSalvas.add(arquivo);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String imagemPadrao = imagensSalvas.isEmpty() ? null : imagensSalvas.get(0);

        Produto produto = new Produto();
        produto.setNome(produtoDto.getNome());
        produto.setPreco(produtoDto.getPreco());
        produto.setMarca(produtoDto.getMarca());
        produto.setSerie(produtoDto.getSerie());
        produto.setColecao(produtoDto.getColecao());
        produto.setCor(produtoDto.getCor());
        produto.setEstilo(produtoDto.getEstilo());
        produto.setQuantidade_estoque(produtoDto.getQuantidade_estoque());
        produto.setDescricao(produtoDto.getDescricao());
        produto.setSessao(produtoDto.getSessao());
        produto.setStatus("ATIVO");
        produto.setImagens(imagensSalvas);
        produto.setImagemPadrao(imagemPadrao);
        produto.setNovidade(true);
        produto.setMaisVendido(false);
        produto.setDesconto(false);
        produto.setDestaque(false);

        produtoRepository.save(produto);

        return "redirect:/backoffice/produtos";
    }

    @GetMapping("/editarproduto")
    public String mostraEdicao(Model model, @RequestParam Long id, HttpSession session) {
        String cargoUsuario = (String) session.getAttribute("cargo");

        if (cargoUsuario == null) {
            return "redirect:/backoffice/login";
        }

        try {
            Produto produto = produtoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
            ProdutoDto produtoDto = new ProdutoDto();
            produtoDto.setId(produto.getId());
            produtoDto.setNome(produto.getNome());
            produtoDto.setPreco(produto.getPreco());
            produtoDto.setMarca(produto.getMarca());
            produtoDto.setSerie(produto.getSerie());
            produtoDto.setColecao(produto.getColecao());
            produtoDto.setCor(produto.getCor());
            produtoDto.setEstilo(produto.getEstilo());
            produtoDto.setQuantidade_estoque(produto.getQuantidade_estoque());
            produtoDto.setDescricao(produto.getDescricao());
            produtoDto.setStatus(produto.getStatus());
            produtoDto.setSessao(produto.getSessao());

            model.addAttribute("produtoDto", produtoDto);

            List<String> imagens = produto.getImagens();
            model.addAttribute("imagens", imagens);

        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
            return "redirect:/backoffice/produtos";
        }
        return "backoffice/editaProduto";
    }

    @PostMapping("/editarproduto")
    public String editarProduto(@ModelAttribute("produtoDto") @Valid ProdutoDto produtoDto, BindingResult bindingResult,
            Model model, HttpSession session) {

        String cargoUsuario = (String) session.getAttribute("cargo");

        if (cargoUsuario == null) {
            return "redirect:/backoffice/login";
        }

        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().forEach(error -> {
                System.out.println("Erro no campo " + error.getField() + ": " + error.getDefaultMessage());
            });
            return "backoffice/editaProduto";
        }

        try {
            Produto produto = produtoRepository.findById(produtoDto.getId())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

            produto.setNome(produtoDto.getNome());
            produto.setPreco(produtoDto.getPreco());
            produto.setMarca(produtoDto.getMarca());
            produto.setSerie(produtoDto.getSerie());
            produto.setColecao(produtoDto.getColecao());
            produto.setCor(produtoDto.getCor());
            produto.setSessao(produtoDto.getSessao());
            produto.setEstilo(produtoDto.getEstilo());
            produto.setQuantidade_estoque(produtoDto.getQuantidade_estoque());
            produto.setDescricao(produtoDto.getDescricao());

            if (produto.getImagens().size() == 1 && produtoDto.getImagensRemovidas() != null
                    && !produtoDto.getImagensRemovidas().isEmpty()) {
                bindingResult.rejectValue("imagensRemovidas", "error.produto",
                        "Não é permitido remover a única imagem associada ao produto.");
                return "backoffice/editaProduto";
            }

            if (produto.getImagens().isEmpty() && (produtoDto.getImagens() == null || produtoDto.getImagens().isEmpty()
                    || produtoDto.getImagens().stream().allMatch(MultipartFile::isEmpty))) {
                bindingResult.rejectValue("imagens", "error.produto", "Não é permitido deixar o produto sem imagem.");
                return "backoffice/editaProduto";
            }

            if (produtoDto.getImagemPadrao() != null && !produtoDto.getImagemPadrao().isEmpty()) {
                produto.setImagemPadrao(produtoDto.getImagemPadrao());
            }

            if (produtoDto.getImagensRemovidas() != null && !produtoDto.getImagensRemovidas().isEmpty()) {
                for (String nomeImagemRemovida : produtoDto.getImagensRemovidas()) {
                    produto.getImagens().remove(nomeImagemRemovida);

                    if (nomeImagemRemovida.equals(produto.getImagemPadrao())) {
                        if (!produto.getImagens().isEmpty()) {
                            produto.setImagemPadrao(produto.getImagens().get(0));
                        } else {
                            produto.setImagemPadrao("");
                        }
                    }

                    String diretorioImagens = "src/main/resources/static/imagens_dos_produtos/";
                    Path imagemRemovidaPath = Paths.get(diretorioImagens + nomeImagemRemovida);
                    Files.deleteIfExists(imagemRemovidaPath);
                }
            }

            List<MultipartFile> novasImagens = produtoDto.getImagens();
            if (novasImagens != null && !novasImagens.isEmpty()) {
                for (MultipartFile imagem : novasImagens) {
                    if (!imagem.isEmpty()) {
                        String nomeArquivo = UUID.randomUUID().toString() + "_" + imagem.getOriginalFilename();
                        try {
                            String diretorioImagens = "src/main/resources/static/imagens_dos_produtos/";
                            Path uploadPath = Paths.get(diretorioImagens);
                            if (!Files.exists(uploadPath)) {
                                Files.createDirectories(uploadPath);
                            }
                            Path filePath = uploadPath.resolve(nomeArquivo);
                            Files.copy(imagem.getInputStream(), filePath);
                            produto.getImagens().add(nomeArquivo);
                        } catch (IOException e) {
                            System.out.println("Erro ao salvar imagem: " + e.getMessage());
                            e.printStackTrace();
                        }
                    }
                }
            }
            produtoRepository.save(produto);

            return "redirect:/backoffice/produtos";
        } catch (RuntimeException ex) {
            System.out.println("Produto não encontrado: " + ex.getMessage());
            ex.printStackTrace();
            return "redirect:/backoffice/produtos";
        } catch (Exception ex) {
            System.out.println("Erro ao editar o produto: " + ex.getMessage());
            ex.printStackTrace();
            return "redirect:/backoffice/produtos";
        }
    }

    @PostMapping("/atualizarStatus")
    public String atualizaStatus(@RequestParam Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("produto não encontrado"));
        produto.setStatus("ATIVO".equals(produto.getStatus()) ? "INATIVO" : "ATIVO");
        produtoRepository.save(produto);
        return "redirect:/backoffice/produtos";
    }

    @PostMapping("/novidade")
    public String definirNovidade(@RequestParam Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("produto não encontrado"));
        if (produto.getNovidade() == true) {
            produto.setNovidade(false);

        } else if (produto.getNovidade() == false) {
            produto.setNovidade(true);
        }

        produtoRepository.save(produto);
        return "redirect:/backoffice/produtos";
    }

    @PostMapping("/destaque")
    public String definirDestaque(@RequestParam Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("produto não encontrado"));
        if (produto.getDestaque() == true) {
            produto.setDestaque(false);

        } else if (produto.getDestaque() == false) {
            produto.setDestaque(true);
        }

        produtoRepository.save(produto);
        return "redirect:/backoffice/produtos";
    }

    @PostMapping("/maisVendido")
    public String definirMaisVendido(@RequestParam Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("produto não encontrado"));
        if (produto.getMaisVendido() == true) {
            produto.setMaisVendido(false);

        } else if (produto.getMaisVendido() == false) {
            produto.setMaisVendido(true);
        }

        produtoRepository.save(produto);
        return "redirect:/backoffice/produtos";
    }

    @GetMapping("/banner")
    public String newBanner(Model model, HttpSession session) {

        String cargoUsuario = (String) session.getAttribute("cargo");

        if (session == null || cargoUsuario == null) {
            return "redirect:/backoffice/login";
        }

        model.addAttribute("banner", new Banner());

        return "backoffice/banners";
    }

    @PostMapping("/cadastraBanner")
    public String cadastraBanner(@ModelAttribute("bannerDTO") @Valid BannerDTO bannerDTO,
            BindingResult bindingResult,
            Model model,
            HttpSession session) {

        if (session == null || session.getAttribute("cargo") == null) {
            return "redirect:/backoffice/login";
        }

        if (bindingResult.hasErrors()) {
            return "backoffice/criaBanner";
        }

        // Verifica se a imagem foi enviada
        if (bannerDTO.getImagem() == null || bannerDTO.getImagem().isEmpty()) {
            bindingResult.rejectValue("imagem", "error.banner", "É necessário selecionar uma imagem.");
            return "backoffice/criaBanner";
        }

        // Salva a imagem no diretório
        String arquivo = UUID.randomUUID().toString() + "_" + bannerDTO.getImagem().getOriginalFilename();
        File diretorio = new File("src/main/resources/static/imagens_banners");

        if (!diretorio.exists()) {
            if (diretorio.mkdirs()) {
                System.out.println("Diretório " + diretorio.getAbsolutePath() + " foi criado.");
            } else {
                System.out.println("Falha ao criar o diretório " + diretorio.getAbsolutePath());
            }
        }

        try {
            String caminhoApp = new File("").getAbsolutePath();
            Path uploadPath = Paths.get(caminhoApp, "src/main/resources/static/imagens_banners");
            Path filePath = uploadPath.resolve(arquivo);
            Files.copy(bannerDTO.getImagem().getInputStream(), filePath);

            Banner banner = new Banner();
            banner.setTitulo(bannerDTO.getTitulo());
            banner.setImagem(arquivo);
            banner.setTipoAtributo(bannerDTO.getTipoAtributo());
            banner.setValorAtributo(bannerDTO.getValorAtributo());
            banner.setStatus(bannerDTO.getStatus());
            banner.setSessao(bannerDTO.getSessao());

            bannerRepository.save(banner);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/backoffice/home";
    }
}
