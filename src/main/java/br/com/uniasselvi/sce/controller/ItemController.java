package br.com.uniasselvi.sce.controller;

import br.com.uniasselvi.sce.dto.ItemRequests;
import br.com.uniasselvi.sce.model.Categoria;
import br.com.uniasselvi.sce.model.Item;
import br.com.uniasselvi.sce.repository.CategoriaRepository;
import br.com.uniasselvi.sce.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("itens")
public class ItemController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ItemRepository itemRepository;

    @GetMapping("{idCategoria}")
    public String items(@PathVariable Long idCategoria, ItemRequests itemRequests, Model model){
        List<Item> items = itemRepository.findItemByCategoria(idCategoria);
        if(!items.isEmpty()){
            model.addAttribute("itens", items);
        }
        Categoria categoria = categoriaRepository.findCategoriaById(idCategoria);
        model.addAttribute("tituloCategoria", categoria.getTitulo());
        model.addAttribute("idCategoria", idCategoria);
        return "items";
    }

    @PostMapping
    public String cadastra(Long id, ItemRequests itemRequests, BindingResult categoriaResult, RedirectAttributes attr){

        try{
            Categoria categoria = categoriaRepository.findCategoriaById(id);
            if (itemRequests.getCodigo() == null
                || itemRequests.getDescricao().isEmpty()
                || itemRequests.getTitulo().isEmpty()
                || itemRequests.getQuantidade() == null){

                attr.addFlashAttribute("mensagemErro", "Todos os campos são obrigatórios.");
                return "redirect:/itens/" + id;
            }else if(itemRepository.findItemByCodigo(itemRequests.getCodigo()) != null){
                attr.addFlashAttribute("mensagemErro", "O sistema já possui um item cadastrado com o mesmo código.");
                return "redirect:/itens/" + id;
            }

            Item item = itemRequests.cadastraItem(categoria);
            itemRepository.save(item);
        }catch (Exception ex){
            System.out.println("Erro: " + ex);
            attr.addFlashAttribute("mensagemErro", "Erro ao cadastrar item");
        }

        return "redirect:/itens/" + id;
    }

    @PostMapping("editar/{idCategoria}/{idItem}")
    public String editar(@PathVariable Long idCategoria, @PathVariable Long idItem, ItemRequests itemRequests, RedirectAttributes attr){

        try{
            Item itemEditado = itemRepository.findItemById(idItem);
            itemEditado.setTitulo(itemRequests.getTitulo());
            itemEditado.setDescricao(itemRequests.getDescricao());
            itemEditado.setQuantidade(itemRequests.getQuantidade());
            itemEditado.setCodigo(itemRequests.getCodigo());
            itemRepository.save(itemEditado);
            attr.addFlashAttribute("mensagemSucesso", "Item editado com sucesso!");
        }catch(Exception ex){
            System.out.println("Erro: " + ex);
            attr.addFlashAttribute("mensagemErro", "Erro ao editar o item");
        }
        return "redirect:/itens/" + idCategoria;
    }

    @GetMapping("estoque/{idCategoria}/{idItem}")
    public String adicionaEstoque(@PathVariable Long idCategoria, @PathVariable Long idItem, @RequestParam(value = "quantidade") Long quantidade, @RequestParam(value = "acao") Long acao, ItemRequests itemRequests, RedirectAttributes attr){
        try{
            if(acao==1){
                Item item = itemRepository.findItemById(idItem);
                Long novaQuantidade = item.getQuantidade() + quantidade;
                item.setQuantidade(novaQuantidade);
                itemRepository.save(item);
                attr.addFlashAttribute("mensagemSucesso", "Estoque atualizado com sucesso");
            }else if(acao == 2){
                Item item = itemRepository.findItemById(idItem);
                if(item.getQuantidade() < quantidade){
                    attr.addFlashAttribute("mensagemErro", "O estoque atual é menor que a quantidade a ser retirada");
                    return "redirect:/itens/" + idCategoria;
                }else{
                    Long novaQuantidade = item.getQuantidade() - quantidade;
                    item.setQuantidade(novaQuantidade);
                    itemRepository.save(item);
                    attr.addFlashAttribute("mensagemSucesso", "Estoque atualizado com sucesso");
                }
            }

        }catch (Exception ex){
            System.out.println("Erro: " + ex);
            attr.addFlashAttribute("mensagemErro", "Erro ao atualizar estoque");
        }
        return "redirect:/itens/" + idCategoria;
    }

    @GetMapping("deletar/{idCategoria}/{idItem}")
    public String deletar(@PathVariable Long idCategoria, @PathVariable Long idItem, RedirectAttributes attr){
        try{
            itemRepository.deleteItemById(idItem);
            attr.addFlashAttribute("mensagemSucesso", "Item excluido com sucesso!");
        }catch (Exception ex){
            System.out.println("Erro: " + ex);
            attr.addFlashAttribute("mensagemErro", "Erro ao excluir o item");
        }
        return "redirect:/itens/" + idCategoria;
    }

}
