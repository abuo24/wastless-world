package uz.wastlessworld.app.controller;// Author - Orifjon Yunusjonov
// t.me/coderr24

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.wastlessworld.app.entity.helpers.Status;
import uz.wastlessworld.app.model.Result;
import uz.wastlessworld.app.payload.CategoryPayload;
import uz.wastlessworld.app.payload.OrderPayload;
import uz.wastlessworld.app.payload.UserPayload;
import uz.wastlessworld.app.repository.UserRepository;
import uz.wastlessworld.app.service.CategoryService;
import uz.wastlessworld.app.service.OrderService;
import uz.wastlessworld.app.service.UserService;

import java.util.UUID;


@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@CrossOrigin(maxAge = 3600, origins = "*")
public class AdminController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final CategoryService categoryService;
    private final OrderService orderService;


    @PutMapping("/user/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody UserPayload userPayload) {
        return userService.editById(id, userPayload);
    }


    @GetMapping("/")
    public ResponseEntity<?> getAll(@RequestParam int page, @RequestParam int size) {
        return ResponseEntity.ok(userRepository.findAll(PageRequest.of(page, size)));
    }


    /**
     * Category
     */

    @PostMapping("/category/")
    public ResponseEntity<Result> saveCategory(@RequestBody CategoryPayload categoryPayload) {
        Result result = categoryService.saveCategory(categoryPayload);
        return ResponseEntity.status(result.isSuccess() ? 200 : 409).body(result);
    }

    @PutMapping("/category/{id}")
    public ResponseEntity<Result> editCategory(@PathVariable UUID id, @RequestBody CategoryPayload categoryPayload) {
        Result result = categoryService.editCategory(id, categoryPayload);
        return ResponseEntity.status(result.isSuccess() ? 200 : 409).body(result);
    }

    @DeleteMapping("/category/{id}")
    public ResponseEntity<Result> deleteCategory(@PathVariable UUID id) {
        Result result = categoryService.deleteCategory(id);
        return ResponseEntity.status(result.isSuccess() ? 200 : 409).body(result);
    }

    /**
     * Order
     */

    @PostMapping("/order/")
    public ResponseEntity<Result> saveOrder(@RequestBody OrderPayload orderPayload) {
        Result result = orderService.saveOrder(orderPayload);
        return ResponseEntity.status(result.isSuccess() ? 200 : 409).body(result);
    }

    @DeleteMapping("/order/{id}")
    public ResponseEntity<Result> deleteOrder(@PathVariable UUID id) {
        Result result = orderService.deleteOrder(id);
        return ResponseEntity.status(result.isSuccess() ? 200 : 409).body(result);
    }

    @GetMapping("/order/")
    public ResponseEntity<Result> changeStatus(@RequestParam("orderId") UUID orderId,
                                               @RequestParam("status") Status status) {
        Result result = orderService.statusChange(orderId, status);
        return ResponseEntity.status(result.isSuccess() ? 200 : 409).body(result);
    }

}
