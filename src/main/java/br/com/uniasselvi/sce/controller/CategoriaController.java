package br.com.uniasselvi.sce.controller;

import br.com.uniasselvi.sce.dto.CategoriaRequests;
import br.com.uniasselvi.sce.model.Categoria;
import br.com.uniasselvi.sce.repository.CategoriaRepository;
import br.com.uniasselvi.sce.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping
public class CategoriaController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ItemRepository itemRepository;

    @GetMapping
    public String inicio(CategoriaRequests categoriaRequests, Model model){
        List<Categoria> categorias = categoriaRepository.findCategorias();
        if(!categorias.isEmpty()){
            model.addAttribute("categorias", categorias);
        }
        return "index";
    }

    @PostMapping
    public String cadastra(CategoriaRequests categoriaRequests, BindingResult categoriaResult, RedirectAttributes attr) {
        try{
            Categoria categoria = categoriaRequests.cadastraCategoria();
            if (categoriaRequests.getTitulo().isEmpty() || categoriaRequests.getDescricao().isEmpty()){
                attr.addFlashAttribute("mensagemErro", "Os campos Título e Descrição não podem estar em branco.");
                return "redirect:/";
            }
            categoriaRepository.save(categoria);
        }catch (Exception ex){
            System.out.println("Erro: " + ex);
            attr.addFlashAttribute("mensagemErro", "Erro ao cadastrar a categoria!");
        }
        return "redirect:/";
    }

    @GetMapping("deletar/{idCategoria}")
    public String deletar(@PathVariable Long idCategoria, RedirectAttributes attr){
        if(itemRepository.findItemByCategoria(idCategoria).isEmpty()){
            try{
                categoriaRepository.deleteCategoriaById(idCategoria);
                attr.addFlashAttribute("mensagemSucesso", "Categoria deletada com sucesso!");
            }catch (Exception ex){
                System.out.println("Erro: " + ex);
                attr.addFlashAttribute("mensagemErro", "Erro ao deletar categoria!");
            }
        }else{
            attr.addFlashAttribute("mensagemErro", "Existem Itens associados a esta categoria...");
        }
        return "redirect:/";
    }

    @PostMapping("editar")
    public String editar(Long id, CategoriaRequests categoriaRequests, BindingResult categoriaResults, RedirectAttributes attr){
        try{
            Categoria categoriaEditada = categoriaRepository.findCategoriaById(id);
            categoriaEditada.setTitulo(categoriaRequests.getTitulo());
            categoriaEditada.setDescricao(categoriaRequests.getDescricao());
            categoriaRepository.save(categoriaEditada);
            attr.addFlashAttribute("mensagemSucesso", "Categoria Editada com sucesso!");
        }catch (Exception ex){
            System.out.println("Erro: " + ex);
            attr.addFlashAttribute("mensagemErro", "Erro ao editar categoria!");
        }
        return "redirect:/";
    }
}
