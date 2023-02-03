package pl.shop.order.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.shop.order.model.dto.OrderDto;
import pl.shop.order.model.dto.OrderSummary;
import pl.shop.order.service.OrderService;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/orders")
    public OrderSummary placeOrder(@RequestBody OrderDto orderDto){
        return orderService.placeOrder(orderDto);
    }
}
