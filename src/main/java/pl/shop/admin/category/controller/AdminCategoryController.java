package pl.shop.admin.category.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.shop.admin.category.controller.dto.AdminCategoryDto;
import pl.shop.admin.category.model.AdminCategory;
import pl.shop.admin.category.service.AdminCategoryService;
import pl.shop.admin.common.utils.SlugifyUtils;

import java.util.List;

@RestController
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
public class AdminCategoryController {

    public static final Long EMPTY_ID = null;
    private final AdminCategoryService adminCategoryService;

    @GetMapping
    public List<AdminCategory> getCategories(){
        return adminCategoryService.getCategories();
    }

    @GetMapping("/{id}")
    public AdminCategory getCategory(@PathVariable Long id){
        return adminCategoryService.getCategory(id);
    }

    @PostMapping
    public AdminCategory createCategory(@RequestBody AdminCategoryDto adminCategoryDto){
        return adminCategoryService.createCategory(mapToAdminCategory(adminCategoryDto, EMPTY_ID ));
    }

    @PutMapping("/{id}")
    public AdminCategory updateCategory(@PathVariable Long id,@RequestBody AdminCategoryDto adminCategoryDto){
        return adminCategoryService.updateCategory(mapToAdminCategory(adminCategoryDto, id));
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id){
        adminCategoryService.deleteCategory(id);
    }

    private AdminCategory mapToAdminCategory(AdminCategoryDto adminCategoryDto, Long id) {//metoda zmniejsza ilosc kodu, korzysta z niej POST i PUT
        return AdminCategory.builder()
                .id(id)
                .name(adminCategoryDto.getName())
                .description(adminCategoryDto.getDescription())
                .slug(SlugifyUtils.slugifySlug(adminCategoryDto.getSlug()))
                .build();
    }

}
