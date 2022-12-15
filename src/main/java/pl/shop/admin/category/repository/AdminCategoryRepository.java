package pl.shop.admin.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.shop.admin.category.model.AdminCategory;


public interface AdminCategoryRepository extends JpaRepository<AdminCategory, Long> {
}
