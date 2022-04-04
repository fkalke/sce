package br.com.uniasselvi.sce.dto;

import br.com.uniasselvi.sce.model.Categoria;

public class CategoriaRequests {

    private String titulo;
    private String descricao;

    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Categoria cadastraCategoria(){
        Categoria categoria = new Categoria();
        categoria.setTitulo(titulo);
        categoria.setDescricao(descricao);
        return categoria;
    }
}
