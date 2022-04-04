package br.com.uniasselvi.sce.dto;

import br.com.uniasselvi.sce.model.Categoria;
import br.com.uniasselvi.sce.model.Item;

public class ItemRequests {

    private String titulo;
    private String descricao;
    private Long codigo;
    private Long quantidade;

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
    public Long getCodigo() {
        return codigo;
    }
    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }
    public Long getQuantidade() {
        return quantidade;
    }
    public void setQuantidade(Long quantidade) {
        this.quantidade = quantidade;
    }

    public Item cadastraItem(Categoria categoria){
        Item item = new Item();
        item.setTitulo(titulo);
        item.setDescricao(descricao);
        item.setCodigo(codigo);
        item.setQuantidade(quantidade);
        item.setCategoria(categoria);
        return item;
    }

}
