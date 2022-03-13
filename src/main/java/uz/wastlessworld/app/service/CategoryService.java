package uz.wastlessworld.app.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import uz.wastlessworld.app.entity.Category;
import uz.wastlessworld.app.exceptions.ResourceNotFoundException;
import uz.wastlessworld.app.model.Result;
import uz.wastlessworld.app.payload.CategoryPayload;
import uz.wastlessworld.app.repository.BasketRepository;
import uz.wastlessworld.app.repository.CategoryRepository;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final Result result;
    private final CategoryRepository categoryRepository;
    private final BasketRepository basketRepository;
    private final BasketService basketService;
    private final MeasurementService measurementService;
    private final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    public Result saveCategory(CategoryPayload categoryPayload) {
        try {
            Category category = new Category();
            category.setImageUrl(categoryPayload.getImageUrl());
            category.setNameRu(categoryPayload.getNameRu());
            category.setNameUz(categoryPayload.getNameUz());
            category.setMeasurements(categoryPayload.getMeasurements().stream().map(item -> measurementService.saveMeasurement(item)).collect(Collectors.toList()));

            categoryRepository.save(category);
            return result.success(category);
        } catch (Exception e) {
            return result.error(e);
        }
    }

    public Result editCategory(UUID categoryId, CategoryPayload categoryPayload) {
        try {
            Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("category not found: " + categoryId));
            if (categoryPayload.getImageUrl() != null)
                category.setImageUrl(categoryPayload.getImageUrl());
            if (categoryPayload.getMeasurements() != null)
                category.setMeasurements(categoryPayload.getMeasurements().stream().map(item -> measurementService.saveMeasurement(item)).collect(Collectors.toList()));
            if (categoryPayload.getNameRu() != null)
                category.setNameRu(categoryPayload.getNameRu());
            if (categoryPayload.getNameUz() != null)
                category.setNameUz(categoryPayload.getNameUz());

            categoryRepository.save(category);
            return result.success(category);
        } catch (Exception e) {
            return result.error(e);
        }
    }

    public Result getMeasurements(UUID categoryId) {
        return result.success(categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("category not found")).getMeasurements());
    }

    public Result deleteCategory(UUID categoryId) {
        try {
            System.out.println(basketRepository.findByCategory_id(categoryId));
            basketRepository.findByCategory_id(categoryId).forEach(basket -> {
                basketService.deleteBasket(basket.getId());
            });
            categoryRepository.deleteById(categoryId);
            return result.delete();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return result.error(e);
        }
    }

    public Result getAllCategory() {
        return result.success(categoryRepository.findAll());
    }

}
