package pl.shop.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.shop.cart.model.CartItem;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Long countByCartId(Long cartId);

    @Query("delete from CartItem ci where ci.cartId=:cartId")
    @Modifying
    void deleteByCartId(Long cartId);

    @Query(value = "delete from CartItem ci where ci.cartId in (:ids)")
    @Modifying
    void deleteAllByCartIdIn(@Param("ids")List<Long> ids);
}
