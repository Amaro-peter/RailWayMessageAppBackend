package com.amaro.apirestfulv1.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String imagem;
    private String nome;
    private String slug;
    private String descricao;
    private boolean disponivel;
    private int qtdEstoque;
    private BigDecimal preco;
    private LocalDate dataCadastro;

    @ManyToOne
    private Categoria categoria;

    public Produto(String imagem, String nome, String slug, String descricao,
                   boolean disponivel, int qtdEstoque, BigDecimal preco, LocalDate dataCadastro, Categoria categoria) {
        this.imagem = imagem;
        this.nome = nome;
        this.slug = slug;
        this.descricao = descricao;
        this.disponivel = disponivel;
        this.qtdEstoque = qtdEstoque;
        this.preco = preco;
        this.dataCadastro = dataCadastro;
        this.categoria = categoria;
    }

}
