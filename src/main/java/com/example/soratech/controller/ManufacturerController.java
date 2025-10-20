package com.example.soratech.controller;

import com.example.soratech.model.Manufacturer;
import com.example.soratech.service.ManufacturerService;
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
@RequestMapping("/manufacturers")
public class ManufacturerController {

    private final ManufacturerService manufacturerService;

    public ManufacturerController(ManufacturerService manufacturerService) {
        this.manufacturerService = manufacturerService;
    }

    // Список производителей с пагинацией и поиском
    @GetMapping
    public String listManufacturers(Model model,
                                    @RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size,
                                    @RequestParam(required = false) String query,
                                    @RequestParam(defaultValue = "name") String sortBy,
                                    @RequestParam(defaultValue = "asc") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
            Sort.by(sortBy).descending() : 
            Sort.by(sortBy).ascending();
        
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Manufacturer> manufacturersPage = query != null && !query.isEmpty()
                ? manufacturerService.searchByName(query, pageable)
                : manufacturerService.findAllActive(pageable);

        model.addAttribute("manufacturers", manufacturersPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", manufacturersPage.getTotalPages());
        model.addAttribute("query", query);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("totalElements", manufacturersPage.getTotalElements());
        return "manufacturers/list";
    }

    // Форма создания
    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("manufacturer", new Manufacturer());
        return "manufacturers/form";
    }

    // Сохранение
    @PostMapping("/create")
    public String createManufacturer(@Valid @ModelAttribute Manufacturer manufacturer,
                                    BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("manufacturer", manufacturer);
            return "manufacturers/form";
        }
        manufacturerService.save(manufacturer);
        return "redirect:/manufacturers";
    }

    // Форма редактирования
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Manufacturer manufacturer = manufacturerService.findById(id);
        model.addAttribute("manufacturer", manufacturer);
        return "manufacturers/form";
    }

    // Обновление
    @PostMapping("/edit/{id}")
    public String updateManufacturer(@PathVariable Long id,
                                    @Valid @ModelAttribute Manufacturer manufacturer,
                                    BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("manufacturer", manufacturer);
            return "manufacturers/form";
        }
        manufacturerService.update(id, manufacturer);
        return "redirect:/manufacturers";
    }

    // Страница деталей
    @GetMapping("/details/{id}")
    public String details(@PathVariable Long id, Model model) {
        Manufacturer manufacturer = manufacturerService.findById(id);
        model.addAttribute("manufacturer", manufacturer);
        return "manufacturers/details";
    }

    // Логическое удаление
    @PostMapping("/logic-delete/{id}")
    public String logicDelete(@PathVariable Long id) {
        manufacturerService.logicDelete(id);
        return "redirect:/manufacturers";
    }

    // Множественное логическое/физическое удаление
    @PostMapping("/delete-multiple")
    public String deleteMultiple(@RequestParam(required = false) List<Long> ids,
                                @RequestParam String action) {
        if (ids != null && !ids.isEmpty()) {
            if ("physical".equals(action)) {
                manufacturerService.deleteAllByIds(ids);
            } else {
                manufacturerService.logicDeleteAllByIds(ids);
            }
        }
        return "redirect:/manufacturers";
    }

    // Страница удалённых производителей
    @GetMapping("/deleted")
    public String deletedManufacturers(Model model,
                                      @RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size,
                                      @RequestParam(required = false) String query,
                                      @RequestParam(defaultValue = "name") String sortBy,
                                      @RequestParam(defaultValue = "asc") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
            Sort.by(sortBy).descending() : 
            Sort.by(sortBy).ascending();
        
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Manufacturer> deletedPage = query != null && !query.isEmpty()
                ? manufacturerService.searchDeletedByName(query, pageable)
                : manufacturerService.findAllDeleted(pageable);
                
        model.addAttribute("deletedManufacturers", deletedPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", deletedPage.getTotalPages());
        model.addAttribute("query", query);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("totalElements", deletedPage.getTotalElements());
        return "manufacturers/deleted";
    }

    // Восстановление
    @PostMapping("/restore/{id}")
    public String restoreManufacturer(@PathVariable Long id) {
        manufacturerService.restore(id);
        return "redirect:/manufacturers/deleted";
    }

    // Физическое удаление
    @PostMapping("/delete/{id}")
    public String deleteManufacturer(@PathVariable Long id) {
        manufacturerService.delete(id);
        return "redirect:/manufacturers/deleted";
    }
}