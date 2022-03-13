package uz.wastlessworld.app.repository;// Author - Orifjon Yunusjonov
// t.me/coderr24

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.wastlessworld.app.entity.Order;
import uz.wastlessworld.app.entity.helpers.Status;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {

    Order findByBaskets_id(UUID basketId);

    List<Order> findByUser_id(UUID userId);

    List<Order> findAllByStatusAndBuyUserIsNull(Status status);
    List<Order> findAllByBuyUser_Username(String username);

}
