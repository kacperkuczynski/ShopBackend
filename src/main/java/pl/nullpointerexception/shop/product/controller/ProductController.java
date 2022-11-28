package pl.nullpointerexception.shop.product.controller;

import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.nullpointerexception.shop.product.model.Product;
import pl.nullpointerexception.shop.product.service.ProductService;

import javax.validation.constraints.Pattern;

@RestController
@RequiredArgsConstructor
@Validated
public class ProductController {

    private final ProductService productService;

    @GetMapping("/products")
    public Page<Product> getProducts(Pageable pageable){
        return productService.getProducts(pageable);
    }

    //konkatencja w zapytaniu: '... where slug=' + slug
    //dopoki nie uzywamy konkatencji spring data chroni nasze zapytanie przed atakami sql injection
    @GetMapping("products/{slug}")
    public Product getProductBySlug(
            @PathVariable
            @Pattern(regexp = "[a-z0-9\\-]+")
            @Length(max = 255)
                String slug){
        return productService.getProductBySlug(slug);//uzytkownik moze wykonac atak sql injection
        // czyli atak zlosliwego kodu na bazie danych dlatego
    }



}
