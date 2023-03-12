package pl.shop.admin.order.model.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Getter
public class AdminOrderStats {
    private List<Integer> label;
    private List<BigDecimal> sale;
    private List<Long> order;
    private Long ordersCount;
    private BigDecimal salesSum;

}
