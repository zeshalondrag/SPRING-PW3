package com.example.soratech.controller;

import com.example.soratech.model.ProductDetails;
import com.example.soratech.service.ProductDetailsService;
import com.example.soratech.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/product-details")
public class ProductDetailsController {

    private final ProductDetailsService productDetailsService;
    private final ProductService productService;

    public ProductDetailsController(ProductDetailsService productDetailsService,
                                    ProductService productService) {
        this.productDetailsService = productDetailsService;
        this.productService = productService;
    }

    @GetMapping
    public String listProductDetails(Model model,
                                    @RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size,
                                    @RequestParam(required = false) String query,
                                    @RequestParam(required = false) Long productId,
                                    @RequestParam(defaultValue = "id") String sortBy,
                                    @RequestParam(defaultValue = "asc") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
            Sort.by(sortBy).descending() : 
            Sort.by(sortBy).ascending();
        
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<ProductDetails> productDetailsPage;

        if (query != null && !query.isEmpty()) {
            productDetailsPage = productDetailsService.searchByProductName(query, pageable);
        } else if (productId != null) {
            productDetailsPage = productDetailsService.filterByProduct(productId, pageable);
        } else {
            productDetailsPage = productDetailsService.findAllActive(pageable);
        }

        model.addAttribute("productDetailsList", productDetailsPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productDetailsPage.getTotalPages());
        model.addAttribute("query", query);
        model.addAttribute("productId", productId);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("totalElements", productDetailsPage.getTotalElements());
        model.addAttribute("products", productService.findAllActive());
        return "product-details/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("productDetails", new ProductDetails());
        model.addAttribute("products", productService.findAllActive());
        return "product-details/form";
    }

    @PostMapping("/create")
    public String createProductDetails(@Valid @ModelAttribute ProductDetails productDetails,
                                      BindingResult result,
                                      @RequestParam Long productId,
                                      Model model) {
        if (result.hasErrors()) {
            model.addAttribute("productDetails", productDetails);
            model.addAttribute("products", productService.findAllActive());
            return "product-details/form";
        }
        productDetailsService.save(productDetails, productId);
        return "redirect:/product-details";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        ProductDetails productDetails = productDetailsService.findById(id);
        model.addAttribute("productDetails", productDetails);
        model.addAttribute("products", productService.findAllActive());
        return "product-details/form";
    }

    @PostMapping("/edit/{id}")
    public String updateProductDetails(@PathVariable Long id,
                                      @Valid @ModelAttribute ProductDetails productDetails,
                                      BindingResult result,
                                      @RequestParam Long productId,
                                      Model model) {
        if (result.hasErrors()) {
            model.addAttribute("productDetails", productDetails);
            model.addAttribute("products", productService.findAllActive());
            return "product-details/form";
        }
        productDetailsService.update(id, productDetails, productId);
        return "redirect:/product-details";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable Long id, Model model) {
        ProductDetails productDetails = productDetailsService.findById(id);
        model.addAttribute("productDetails", productDetails);
        return "product-details/details";
    }

    @PostMapping("/logic-delete/{id}")
    public String logicDelete(@PathVariable Long id) {
        productDetailsService.logicDelete(id);
        return "redirect:/product-details";
    }

    @PostMapping("/delete-multiple")
    public String deleteMultiple(@RequestParam(required = false) List<Long> ids,
                                @RequestParam String action) {
        if (ids != null && !ids.isEmpty()) {
            if ("physical".equals(action)) {
                productDetailsService.deleteAllByIds(ids);
            } else {
                productDetailsService.logicDeleteAllByIds(ids);
            }
        }
        return "redirect:/product-details";
    }

    @GetMapping("/deleted")
    public String deletedProductDetails(Model model,
                                       @RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductDetails> deletedPage = productDetailsService.findAllDeleted(pageable);
        model.addAttribute("deletedProductDetails", deletedPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", deletedPage.getTotalPages());
        return "product-details/deleted";
    }

    @PostMapping("/restore/{id}")
    public String restoreProductDetails(@PathVariable Long id) {
        productDetailsService.restore(id);
        return "redirect:/product-details/deleted";
    }

    @PostMapping("/delete/{id}")
    public String deleteProductDetails(@PathVariable Long id) {
        productDetailsService.delete(id);
        return "redirect:/product-details/deleted";
    }
}