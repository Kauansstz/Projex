package com.kauan.projex.controllers;

import java.util.List;



import com.kauan.projex.model.CategoryModel;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kauan.projex.repository.CategoryRepository;
import com.kauan.projex.utils.Category;

import org.springframework.ui.Model;

@Controller
public class CategoryController {

    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/listarCategory")
    public String listar(@RequestParam(required = false) Category categoria, Model model){
        List<CategoryModel> itens;
        if (categoria != null){
            itens = categoryRepository.findByCategory(categoria);

        } else{
            itens = categoryRepository.findAll();
        }
        model.addAttribute("itens", itens);
        model.addAttribute("categorias", Category.values());
        return "lista";
    }

    @GetMapping("/form")
    public String form(Model model){
        model.addAttribute("categorias", Category.values());
        return "pages/form";
    }
}
