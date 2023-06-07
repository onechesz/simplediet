package com.github.onechesz.simplediet.controllers;

import com.github.onechesz.simplediet.dto.dtio.ProductDTIO;
import com.github.onechesz.simplediet.dto.dtoo.ProductDTOO;
import com.github.onechesz.simplediet.services.DietService;
import jakarta.validation.Valid;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping(value = "/diets")
public class DietsController {
    private final DietService dietService;

    @Contract(pure = true)
    public DietsController(DietService dietService) {
        this.dietService = dietService;
    }

    @GetMapping(value = "")
    public String products(@RequestParam(value = "search", required = false) String search, @RequestParam(value = "from", required = false) String from, @RequestParam(value = "to", required = false) String to, @RequestParam(value = "sort", required = false) String sort, @NotNull Model model) {
        model.addAttribute("products", dietService.findAll(search, from, to, sort));

        return "diets/diets";
    }

    @GetMapping(value = "/add")
    public String addProduct(@NotNull Model model) {
        model.addAttribute("product", new ProductDTIO());

        return "diets/add";
    }

    @PostMapping(value = "/add")
    public String addProduct(@ModelAttribute(value = "product") @Valid ProductDTIO productDTIO, @NotNull BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()) return "products/add";

        dietService.save(productDTIO);

        return "redirect:/diets";
    }

    @GetMapping(value = "/{id}")
    public String viewProduct(@PathVariable("id") int productId, @NotNull Model model) {
        ProductDTOO productDTOO = dietService.findById(productId);

        if (productDTOO == null) return "redirect:/diets";

        model.addAttribute("product", productDTOO);

        return "diets/diet";
    }

    @GetMapping(value = "/{id}/edit")
    public String editProduct(@PathVariable("id") int id, @NotNull Model model) {
        ProductDTOO productDTOO = dietService.findById(id);

        if (productDTOO == null) return "redirect:/diets";

        model.addAllAttributes(Map.of("product_dtoo", productDTOO, "product_dtio", new ProductDTIO()));

        return "diets/edit";
    }

    @PatchMapping(value = "/{id}/edit")
    public String editProduct(@PathVariable("id") int id, @ModelAttribute("product") @Valid ProductDTIO productDTIO, @NotNull BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()) return "diets/edit";

        dietService.edit(id, productDTIO);

        return "redirect:/diets";
    }

    @DeleteMapping(value = "/{id}")
    public String deleteProduct(@PathVariable("id") int id) throws IOException {
        dietService.deleteById(id);

        return "redirect:/diets";
    }
}
