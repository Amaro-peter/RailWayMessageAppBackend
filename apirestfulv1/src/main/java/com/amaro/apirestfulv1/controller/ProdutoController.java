package com.amaro.apirestfulv1.controller;


import com.amaro.apirestfulv1.model.Produto;
import com.amaro.apirestfulv1.model.ResultadoPaginado;
import com.amaro.apirestfulv1.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://192.168.15.15:5173", "http://localhost:5173", "http://127.0.0.1:5173", "https://vercel-message-app-front-end.vercel.app/"})
@RequestMapping("produtos") //http://localhost:8080/produtos
public class ProdutoController {
    @Autowired
    private ProdutoService produtoService;

    @GetMapping //requisição do tipo GET para http://localhost:8080/produtos
    public List<Produto> recuperarProduto(){
        return produtoService.recuperarProdutos();
    }

    @GetMapping("{idProduto}")
    public Produto recuperarProdutoPorId(@PathVariable("idProduto") long id)  {
        return produtoService.recuperarProdutoPorId(id);
    }

    // Entradas
    // - pagina corrente
    // - tamanho da página
    // Saídas:
    // - total de itens
    // - total de páginas
    // - pagina corrente
    // - itens da página corrente

    // Requisição do tipo GET para
    // http://localhost:8080/produtos/paginacao?pagina=0&tamanho=5

    @GetMapping("paginacao")
    public ResultadoPaginado<Produto> recuperarProdutosComPaginicao(
            @RequestParam(value = "pagina", defaultValue = "0") int pagina,
            @RequestParam(value = "tamanho", defaultValue = "5") int tamanho,
            @RequestParam(value = "nome", defaultValue = "") String nome
    ) {
        Pageable pageable = PageRequest.of(pagina, tamanho);
        Page<Produto> page = produtoService.recuperarProdutosComPaginacao(pageable, nome);
        ResultadoPaginado<Produto> resultadoPaginado = new ResultadoPaginado<>(
                page.getTotalElements(),
                page.getTotalPages(),
                page.getNumber(),
                page.getContent()
        );
        return resultadoPaginado;
    }

    @GetMapping("categoria/paginacao")
    public ResultadoPaginado<Produto> recuperarProdutosPaginadosPorSlugDaCategoria(
            @RequestParam(value = "pagina", defaultValue = "0") int pagina,
            @RequestParam(value = "tamanho", defaultValue = "3") int tamanho,
            @RequestParam(value = "slugCategoria", defaultValue = "") String slugCategoria
    ) {
        Pageable pageable = PageRequest.of(pagina, tamanho);
        Page<Produto> page = produtoService.recuperarProdutosPaginadosPorSlugDaCategoria(slugCategoria, pageable);
        ResultadoPaginado<Produto> resultadoPaginado = new ResultadoPaginado<>(
                page.getTotalElements(),
                page.getTotalPages(),
                page.getNumber(),
                page.getContent()
        );
        return resultadoPaginado;
    }

    @GetMapping("categoria/{slugCategoria}")
    public List<Produto> recuperarProdutosPorSlugCategoria(@PathVariable("slugCategoria") String slugCategoria) {
        return produtoService.recuperarProdutosPorSlugCategoria(slugCategoria);
    }

    @PostMapping
    public Produto cadastrarProduto(@RequestBody Produto produto) {
        return produtoService.cadastrarProduto(produto);
    }

    @PutMapping
    public Produto alterarProduto(@RequestBody Produto produto) {
        return produtoService.alterarProduto(produto);
    }

    @DeleteMapping("{idProduto}")
    public void removerProduto(@PathVariable("idProduto") long id) {
        produtoService.removerProduto(id);
    }
}
