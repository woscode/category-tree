package com.wos.categorytree;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class CategoryDemoController {

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping({"/", "/index"})
    public String index(Model model) {
        return "index";
    }

    @GetMapping("/new")
    public String registration(Model model) {
        model.addAttribute("category", new Category());
        return "new";
    }

    @PostMapping("/new")
    public String newCategory(@ModelAttribute("category") @Valid Category category, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "new";
        categoryRepository.save(category);
        return "redirect:/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("category", categoryRepository.getById(id));
        return "show";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("category", categoryRepository.getById(id));
        return "edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("category") @Valid Category category, BindingResult bindingResult, @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "show";
        categoryRepository.save(category);
        return "redirect:/index";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        categoryRepository.deleteById(id);
        return "redirect:/index";
    }

    @ModelAttribute("categories")
    public List<Category> allCategories() {
        return categoryRepository.findAll();
    }
}
