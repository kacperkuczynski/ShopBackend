package pl.shop.admin.order.repositor;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.shop.admin.order.model.AdminOrderLog;

public interface AdminOrderLogRepository extends JpaRepository<AdminOrderLog, Long> {
}
