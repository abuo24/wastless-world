package uz.wastlessworld.app.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import uz.wastlessworld.app.entity.Order;
import uz.wastlessworld.app.entity.User;
import uz.wastlessworld.app.entity.helpers.Status;
import uz.wastlessworld.app.exceptions.ResourceNotFoundException;
import uz.wastlessworld.app.model.Result;
import uz.wastlessworld.app.payload.OrderPayload;
import uz.wastlessworld.app.repository.OrderRepository;
import uz.wastlessworld.app.repository.UserRepository;
import uz.wastlessworld.app.security.SecurityUtils;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final BasketService basketService;
    private final CoordinatesService coordinatesService;
    private final UserRepository userRepository;
    private final Result result;
    private final Logger logger = LoggerFactory.getLogger(OrderService.class);

    public Result saveOrder(OrderPayload orderPayload) {
        try {
            Order order = new Order();
//            order.setUser(userRepository.findById(orderPayload.getUserId()).orElseThrow(() -> new ResourceNotFoundException("user not found: " + orderPayload.getUserId())));
            order.setUser(userRepository.findByUsername(SecurityUtils.getCurrentUsername().orElseThrow(() -> new ResourceNotFoundException("user not found"))).orElseThrow(() -> new ResourceNotFoundException("user not found")));
            order.setBaskets(basketService.saveBasket(orderPayload.getBasketPayload()));
            order.setCoordinate(coordinatesService.saveCoordinates(orderPayload.getCoordinatesPayload()));
            order.setStatus(Status.PROCESSING);
            orderRepository.save(order);
            return result.success(order);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return result.error(e);
        }
    }

    public Result getAll() {
        return result.success(orderRepository.findAll());
    }

    public Result getAllAds() {
        List<Order> orders = orderRepository.findAllByStatusAndBuyUserIsNull(Status.PROCESSING);
        return result.success(orders);
    }

    public Result getMeBuyCategories() {
        List<Order> orders = orderRepository.findAllByBuyUser_Username(SecurityUtils.getCurrentUsername().orElseThrow(()-> new ResourceNotFoundException("not found")));
        return result.success(orders);
    }

    public Result deleteOrder(UUID orderId) {
        try {
            orderRepository.deleteById(orderId);
            return result.delete();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return result.error(e);
        }
    }

    public Result statusChange(UUID orderId, Status status) {
        try {
            Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found: " + orderId));
            order.setStatus(status);
            orderRepository.save(order);
            return result.success(order);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return result.error(e);
        }
    }

    public Result addBuyUser(UUID orderId, UUID userId) {
        try {
            Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("order not found: " + orderId));
            User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not found: " + userId));

            order.setBuyUser(user);
            orderRepository.save(order);
            return result.success(order);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return result.error(e);
        }
    }

    public Result getAllStatus() {
        return result.success(Status.values());
    }

    public Result findByUserId(UUID userId) {
        return result.success(orderRepository.findByUser_id(userId));
    }
}
