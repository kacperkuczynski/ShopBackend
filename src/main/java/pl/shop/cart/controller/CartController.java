package pl.shop.cart.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.shop.cart.controller.dto.CartSummaryDto;
import pl.shop.cart.controller.mapper.CartMapper;
import pl.shop.cart.model.dto.CartProductDto;
import pl.shop.cart.service.CartService;

@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping("/{id}")//1 usluga ktora bedzie pobierala nam koszyk po jego id
    public CartSummaryDto CartSummaryDto(@PathVariable Long id){
        return CartMapper.mapToCartSummary(cartService.getCart(id));
    }

    @PutMapping("/{id}")
    public CartSummaryDto addProductToCart(@PathVariable Long id, @RequestBody CartProductDto cartProductDto){
        return CartMapper.mapToCartSummary(cartService.addProductToCart(id, cartProductDto));
    }
}
