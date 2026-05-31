package com.tp.jpa.repository;

import com.tp.jpa.model.Categoria;

public class CategoriaRepository extends BaseRepository<Categoria> {

    // Constructor que llama a super con la Class de Categoria

    public CategoriaRepository() {
        super(Categoria.class);
    }
}
