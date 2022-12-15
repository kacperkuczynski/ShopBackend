package pl.shop.category.dto;

import org.springframework.data.domain.Page;
import pl.shop.common.dto.ProductListDto;
import pl.shop.common.model.Category;

public record CategoryProductsDto(Category category, Page<ProductListDto> products) {
}
