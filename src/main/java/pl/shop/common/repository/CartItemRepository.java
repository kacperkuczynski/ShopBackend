package pl.shop.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.shop.common.model.CartItem;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Long countByCartId(Long cartId);

    @Query("delete from CartItem ci where ci.cartId=:cartId")
    @Modifying
    void deleteByCartId(@Param("cartId")Long cartId);

    @Query(value = "delete from CartItem ci where ci.cartId in (:ids)")
    @Modifying
    void deleteAllByCartIdIn(@Param("ids")List<Long> ids);
}
