package pl.shop.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.shop.order.model.Payment;


public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
