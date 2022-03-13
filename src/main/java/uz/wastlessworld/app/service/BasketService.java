package uz.wastlessworld.app.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import uz.wastlessworld.app.entity.Basket;
import uz.wastlessworld.app.entity.Category;
import uz.wastlessworld.app.entity.MeasurementWeight;
import uz.wastlessworld.app.exceptions.BadRequestException;
import uz.wastlessworld.app.exceptions.ResourceNotFoundException;
import uz.wastlessworld.app.model.Result;
import uz.wastlessworld.app.payload.BasketPayload;
import uz.wastlessworld.app.repository.BasketRepository;
import uz.wastlessworld.app.repository.CategoryRepository;
import uz.wastlessworld.app.repository.MeasurementWeightRepository;
import uz.wastlessworld.app.repository.OrderRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BasketService {
    private final BasketRepository basketRepository;
    private final CategoryRepository categoryRepository;
    private final OrderRepository orderRepository;
    private final MeasurementWeightRepository measurementWeightRepository;
    private final Result result;
    private final Logger logger = LoggerFactory.getLogger(BasketService.class);

    public List<Basket> saveBasketList(List<BasketPayload> basketPayloadList) {
        List<Basket> basketList = new ArrayList<>();
        basketPayloadList.forEach(basketPayload -> {
            basketList.add(saveBasket(basketPayload));
        });
        return basketList;
    }

    public Basket saveBasket(BasketPayload basketPayload) {
        try {
            Basket basket = new Basket();
            Category category = categoryRepository.findById(basketPayload.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("Category not found: " + basketPayload.getCategoryId()));
            basket.setCategory(category);
            basket.setWeights(basketPayload.getMeasurements().stream().map(measurementWeightPayload -> measurementWeightRepository.save(new MeasurementWeight(measurementWeightPayload.getWeight(), measurementWeightPayload.getTypes()))).collect(Collectors.toList()));

            /// total priceni hisoblash kk
//            basket.setTotalPrice(category.getPrice().multiply(basketPayload.getWeight()));
            basket.setTotalPrice(basketPayload.getTotalPrice());
            basketRepository.save(basket);
            return basket;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Something is wrong");
        }
    }

    public Result deleteBasket(UUID basketId) {
        try {
//            orderRepository.delete(orderRepository.findByBaskets_id(basketId));
            Basket basket = basketRepository.findById(basketId).orElseThrow(() -> new ResourceNotFoundException("Basket not found: " + basketId));
            basket.setCategory(null);
            basketRepository.save(basket);
            basketRepository.deleteById(basketId);
            return result.delete();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return result.error(e);
        }
    }

    public Result editBasket(UUID basketId, BasketPayload basketPayload) {
        try {
            Basket basket = basketRepository.findById(basketId).orElseThrow(() -> new ResourceNotFoundException("Basket not found: " + basketId));
            Category category = categoryRepository.findById(basketPayload.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("Category not found: " + basketPayload.getCategoryId()));
            basket.setCategory(category);
            basket.setWeights(basketPayload.getMeasurements().stream().map(measurementWeightPayload -> measurementWeightRepository.save(new MeasurementWeight(measurementWeightPayload.getWeight(), measurementWeightPayload.getTypes()))).collect(Collectors.toList()));
//            total price
//            basket.setTotalPrice(category.getPrice().multiply(basketPayload.getWeight()));
            basket.setTotalPrice(basketPayload.getTotalPrice());
            basketRepository.save(basket);
            return result.success(basket);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return result.error(e);
        }
    }

    public Result getAllBasket() {
        return result.success(basketRepository.findAll());
    }


}
