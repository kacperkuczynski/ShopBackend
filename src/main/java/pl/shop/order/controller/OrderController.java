package pl.shop.order.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.shop.order.model.Order;
import pl.shop.order.model.dto.InitOrder;
import pl.shop.order.model.dto.OrderDto;
import pl.shop.order.model.dto.OrderListDto;
import pl.shop.order.model.dto.OrderSummary;
import pl.shop.order.service.OrderService;
import pl.shop.order.service.PaymentService;
import pl.shop.order.service.ShipmentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final ShipmentService shipmentService;
    private final PaymentService paymentService;

    @PostMapping
    public OrderSummary placeOrder(@RequestBody OrderDto orderDto,
                                   @AuthenticationPrincipal Long userId){
        return orderService.placeOrder(orderDto, userId);
    }

    @GetMapping("/initData")
    public InitOrder initData(){
        return InitOrder.builder()
                .shipment(shipmentService.getShipments())
                .payment(paymentService.getPayments())
                .build();
    }

    @GetMapping
    public List<OrderListDto> getOrders(@AuthenticationPrincipal Long userId){
        if (userId == null){
            throw new IllegalArgumentException("Bral użytkownika");
        }
        return orderService.getOrderForCustomer(userId);
    }
}
