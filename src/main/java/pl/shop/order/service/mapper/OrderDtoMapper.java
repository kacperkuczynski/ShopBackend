package pl.shop.order.service.mapper;

import pl.shop.order.model.Order;
import pl.shop.order.model.dto.OrderListDto;

import java.util.List;

public class OrderDtoMapper {
    public static List<OrderListDto> maptToOrderListDto(List<Order> orders) {
        return orders.stream()
                .map(order -> new OrderListDto(
                        order.getId(),
                        order.getPlaceDate(),
                        order.getOrderStatus().getValue(),
                        order.getGrossValue()))
                .toList()
                ;
    }
}
