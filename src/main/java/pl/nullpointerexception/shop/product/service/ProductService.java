package pl.nullpointerexception.shop.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.nullpointerexception.shop.product.model.Product;
import pl.nullpointerexception.shop.product.repository.ProductRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    public List<Product> getProducts(){
        return productRepository.findAll();
    }
}
