package pl.nullpointerexception.shop.admin.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.nullpointerexception.shop.admin.product.model.AdminProduct;
import pl.nullpointerexception.shop.admin.product.repository.AdminProductRepository;

@Service
@RequiredArgsConstructor
public class AdminProductService {

    private final AdminProductRepository productRepository;
    public Page<AdminProduct> getProducts(Pageable pageable){
        return productRepository.findAll(pageable);
    }
}
