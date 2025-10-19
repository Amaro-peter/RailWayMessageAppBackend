package com.amaro.apirestfulv1.service;

import com.amaro.apirestfulv1.exception.EntidadeNaoEncontradaException;
import com.amaro.apirestfulv1.model.Produto;
import com.amaro.apirestfulv1.repository.ProdutoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public List<Produto> recuperarProdutos() {
        return produtoRepository.recuperarProdutosPorCategoria();
    }

    public Produto recuperarProdutoPorId(Long id)  {
        return produtoRepository.recuperarProdutoPorId(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                "Produto Numero " + id + " nao encontrado."));
    }

    public Page<Produto> recuperarProdutosComPaginacao(Pageable pageable, String nome) {
        return produtoRepository.recuperarProdutosComPaginacao(pageable, "%" + nome + "%");
    }

    public Page<Produto> recuperarProdutosPaginadosPorSlugDaCategoria(String slugCategoria, Pageable pageable) {
        if(!slugCategoria.isEmpty()) {
            return produtoRepository.recuperarProdutosPaginadosPorSlugDaCategoria(slugCategoria, pageable);
        } else {
            return produtoRepository.recuperarProdutosPaginados(pageable);
        }
    }

    public List<Produto> recuperarProdutosPorSlugCategoria(String slugCategoria) {
        return produtoRepository.recuperarProdutosPorSlugCategoria(slugCategoria);
    }

    public Produto cadastrarProduto(Produto produto) {
        return produtoRepository.save(produto);
    }

    @Transactional
    public Produto alterarProduto(Produto produto) throws EntidadeNaoEncontradaException {
        Optional<Produto> opt = produtoRepository.recuperarProdutoComIdLock(produto.getId());
        if(opt.isPresent()) {
            return produtoRepository.save(produto);
        }

        throw new EntidadeNaoEncontradaException("Produto inexistente");
    }

    @Transactional(rollbackFor = Exception.class)
    public void removerProduto(Long id) {
        produtoRepository.deleteById(id);
    }

}
