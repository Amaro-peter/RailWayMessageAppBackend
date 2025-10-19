package com.amaro.apirestfulv1.repository;

import com.amaro.apirestfulv1.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}
