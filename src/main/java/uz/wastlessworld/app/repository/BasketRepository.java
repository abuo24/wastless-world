package uz.wastlessworld.app.repository;// Author - Orifjon Yunusjonov
// t.me/coderr24

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.wastlessworld.app.entity.Basket;

import java.util.List;
import java.util.UUID;

@Repository
public interface BasketRepository extends JpaRepository<Basket, UUID> {
    List<Basket> findByCategory_id(UUID categoryId);
}
