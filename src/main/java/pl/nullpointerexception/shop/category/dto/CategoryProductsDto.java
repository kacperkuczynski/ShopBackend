package pl.nullpointerexception.shop.category.dto;

import org.springframework.data.domain.Page;
import pl.nullpointerexception.shop.common.dto.ProductListDto;
import pl.nullpointerexception.shop.common.model.Category;

public record CategoryProductsDto(Category category, Page<ProductListDto> products) {
}
