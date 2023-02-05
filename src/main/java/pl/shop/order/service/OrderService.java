package pl.shop.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.shop.common.model.Cart;
import pl.shop.common.model.CartItem;
import pl.shop.common.repository.CartItemRepository;
import pl.shop.common.repository.CartRepository;
import pl.shop.order.model.Order;
import pl.shop.order.model.OrderRow;
import pl.shop.order.model.OrderStatus;
import pl.shop.order.model.Payment;
import pl.shop.order.model.Shipment;
import pl.shop.order.model.dto.OrderDto;
import pl.shop.order.model.dto.OrderSummary;
import pl.shop.order.repository.OrderRepository;
import pl.shop.order.repository.OrderRowRepository;
import pl.shop.order.repository.PaymentRepository;
import pl.shop.order.repository.ShipmentRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderRowRepository orderRowRepository;
    private final ShipmentRepository shipmentRepository;
    private final PaymentRepository paymentRepository;

    @Transactional
    public OrderSummary placeOrder(OrderDto orderDto) {
        // pobrać koszyk
        Cart cart = cartRepository.findById(orderDto.getCartId()).orElseThrow();
        Shipment shipment = shipmentRepository.findById(orderDto.getShipmentId()).orElseThrow();
        Payment payment = paymentRepository.findById(orderDto.getPaymentId()).orElseThrow();
        // stworzenie zamówienia z wierszami
        Order order = Order.builder()
                .firstname(orderDto.getFirstname())
                .lastname(orderDto.getLastname())
                .street(orderDto.getStreet())
                .zipcode(orderDto.getZipcode())
                .city(orderDto.getCity())
                .email(orderDto.getEmail())
                .phone(orderDto.getPhone())
                .placeDate(LocalDateTime.now())
                .orderStatus(OrderStatus.NEW)
                .grossValue(calculateGrossValue(cart.getItems(), shipment))
                .payment(payment)
                .build();
        // zapisać zamówienie
        Order newOrder = orderRepository.save(order);
        saveOrderRows(cart, newOrder.getId(),shipment);
        // usunąć koszyk
        cartItemRepository.deleteByCartId(orderDto.getCartId());
        cartRepository.deleteCartById(orderDto.getCartId());
        // zwrócić podsumowanie
        return OrderSummary.builder()
                .id(newOrder.getId())
                .placeDate(newOrder.getPlaceDate())
                .status(newOrder.getOrderStatus())
                .grossValue(newOrder.getGrossValue())
                .payment(payment)
                .build();
    }

    private BigDecimal calculateGrossValue(List<CartItem> items, Shipment shipment) {
        return items.stream()
                .map(cartItem -> cartItem.getProduct().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO)
                .add(shipment.getPrice());
    }

    private void saveOrderRows(Cart cart, Long orderId, Shipment shipment) {
        saveProductRows(cart, orderId);
        saveShipmentRow(orderId, shipment);
    }

    private void saveShipmentRow(Long orderId, Shipment shipment) {
        orderRowRepository.save(OrderRow.builder()
                        .quantity(1)
                        .price(shipment.getPrice())
                        .shipmentId(shipment.getId())
                        .orderId(orderId)
                        .build());
    }

    private void saveProductRows(Cart cart, Long orderId) {
        cart.getItems().stream()
                .map(cartItem -> OrderRow.builder()
                        .quantity(cartItem.getQuantity())
                        .productId(cartItem.getProduct().getId())
                        .price(cartItem.getProduct().getPrice())
                        .orderId(orderId)
                        .build())
                .peek(orderRowRepository::save)
                .toList();
    }
}
