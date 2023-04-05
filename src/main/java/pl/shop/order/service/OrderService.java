package pl.shop.order.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.shop.common.mail.EmailClientService;
import pl.shop.common.model.Cart;
import pl.shop.common.repository.CartItemRepository;
import pl.shop.common.repository.CartRepository;
import pl.shop.order.model.Order;
import pl.shop.order.model.Payment;
import pl.shop.order.model.Shipment;
import pl.shop.order.model.dto.OrderDto;
import pl.shop.order.model.dto.OrderListDto;
import pl.shop.order.model.dto.OrderSummary;
import pl.shop.order.repository.OrderRepository;
import pl.shop.order.repository.OrderRowRepository;
import pl.shop.order.repository.PaymentRepository;
import pl.shop.order.repository.ShipmentRepository;

import java.util.List;

import static pl.shop.order.service.mapper.OrderDtoMapper.maptToOrderListDto;
import static pl.shop.order.service.mapper.OrderEmailMessageMapper.createEmailMessage;
import static pl.shop.order.service.mapper.OrderMapper.createNewOrder;
import static pl.shop.order.service.mapper.OrderMapper.createOrderSummary;
import static pl.shop.order.service.mapper.OrderMapper.mapToOrderRow;
import static pl.shop.order.service.mapper.OrderMapper.mapToOrderRowWithQuantity;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderRowRepository orderRowRepository;
    private final ShipmentRepository shipmentRepository;
    private final PaymentRepository paymentRepository;
    private final EmailClientService emailClientService;

    @Transactional
    public OrderSummary placeOrder(OrderDto orderDto, Long userId) {
        // pobrać koszyk
        Cart cart = cartRepository.findById(orderDto.getCartId()).orElseThrow();
        Shipment shipment = shipmentRepository.findById(orderDto.getShipmentId()).orElseThrow();
        Payment payment = paymentRepository.findById(orderDto.getPaymentId()).orElseThrow();
        // stworzenie zamówienia z wierszami
        Order newOrder = orderRepository.save(createNewOrder(orderDto, cart, shipment, payment, userId));
        saveOrderRows(cart, newOrder.getId(),shipment);
        // usunąć koszyk
        clearOrderCart(orderDto);
        log.info("Zamówienie złożone");
        //wysłanie maila
        sendConfirmEmail(newOrder);
        // zwrócić podsumowanie
        return createOrderSummary(payment, newOrder);
    }

    private void sendConfirmEmail(Order newOrder) {
        emailClientService.getInstance().send(newOrder.getEmail(),
                "Twoje zamówienie zostało przyjęte",
                createEmailMessage(newOrder));
    }

    private void clearOrderCart(OrderDto orderDto) {
        cartItemRepository.deleteByCartId(orderDto.getCartId());
        cartRepository.deleteCartById(orderDto.getCartId());
    }

    private void saveOrderRows(Cart cart, Long orderId, Shipment shipment) {
        saveProductRows(cart, orderId);
        saveShipmentRow(orderId, shipment);
    }

    private void saveShipmentRow(Long orderId, Shipment shipment) {
        orderRowRepository.save(mapToOrderRow(orderId, shipment));
    }

    private void saveProductRows(Cart cart, Long orderId) {
        cart.getItems().stream()
                .map(cartItem -> mapToOrderRowWithQuantity(orderId, cartItem))
                .peek(orderRowRepository::save)
                .toList();
    }

    public List<OrderListDto> getOrderForCustomer(Long userId) {
        return maptToOrderListDto(orderRepository.findByUserId(userId));
    }


}
