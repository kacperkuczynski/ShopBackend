package pl.shop.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.shop.order.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
