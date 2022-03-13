package uz.wastlessworld.app.controller;// Author - Orifjon Yunusjonov
// t.me/coderr24

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.wastlessworld.app.entity.User;
import uz.wastlessworld.app.entity.helpers.Status;
import uz.wastlessworld.app.exceptions.ResourceNotFoundException;
import uz.wastlessworld.app.model.Result;
import uz.wastlessworld.app.payload.OrderPayload;
import uz.wastlessworld.app.payload.UserPayload;
import uz.wastlessworld.app.repository.UserRepository;
import uz.wastlessworld.app.security.SecurityUtils;
import uz.wastlessworld.app.service.CategoryService;
import uz.wastlessworld.app.service.OrderService;
import uz.wastlessworld.app.service.UserService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@CrossOrigin(maxAge = 3600L)
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;
    private final CategoryService categoryService;
    private final OrderService orderService;

    @GetMapping("/me")
    public ResponseEntity<?> getMe() {
        User user = userRepository.findByUsername(SecurityUtils.getCurrentUsername().orElseThrow(() -> new RuntimeException("username not founsd"))).orElseThrow(() -> new RuntimeException("user not found"));
        return ResponseEntity.ok(user);
    }

    @PutMapping("/")
    public ResponseEntity<?> update(@RequestBody UserPayload userPayload) {
        return userService.editById(userRepository.findByUsername(SecurityUtils.getCurrentUsername().orElseThrow(() -> new ResourceNotFoundException("user not found"))).orElseThrow(() -> new ResourceNotFoundException("user not found")).getId(), userPayload);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        userRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/category/")
    public ResponseEntity<Result> getAllCategory() {
        Result result = categoryService.getAllCategory();
        return ResponseEntity.status(result.isSuccess() ? 200 : 409).body(result);
    }

    /**
     * Order
     */

    @GetMapping("/order/")
    public ResponseEntity<Result> getAllOrder() {
        Result result = orderService.getAll();
        return ResponseEntity.status(result.isSuccess() ? 200 : 409).body(result);
    }

    @GetMapping("/ads/")
    public ResponseEntity<?> getAllAds() {
        return ResponseEntity.ok(orderService.getAllAds());
    }

    @GetMapping("/summa/{categoryId}")
    public ResponseEntity<Result> getSumma(@PathVariable("categoryId") UUID categoryId) {
        Result result = categoryService.getMeasurements(categoryId);
        return ResponseEntity.status(result.isSuccess() ? 200 : 409).body(result);
    }

    @GetMapping("/order/find-by-user/{userId}")
    public ResponseEntity<Result> findByUserId(@PathVariable("userId") UUID userId) {
        Result result = orderService.findByUserId(userId);
        return ResponseEntity.status(result.isSuccess() ? 200 : 409).body(result);
    }

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

    @PatchMapping("/order/")
    public ResponseEntity<Result> changeStatus(@RequestParam("orderId") UUID orderId,
                                               @RequestParam("status") Status status) {
        Result result = orderService.statusChange(orderId, status);
        return ResponseEntity.status(result.isSuccess() ? 200 : 409).body(result);
    }

    @PutMapping("/order/")
    public ResponseEntity<Result> addBuyUser(@RequestParam("orderId") UUID orderId,
                                             @RequestParam("userId") UUID userId) {
        Result result = orderService.addBuyUser(orderId, userId);
        return ResponseEntity.status(result.isSuccess() ? 200 : 409).body(result);
    }

    @GetMapping("/order/buy-categories/")
    public ResponseEntity<Result> getMeBuyCategories() {
        Result result = orderService.getMeBuyCategories();
        return ResponseEntity.status(result.isSuccess() ? 200 : 409).body(result);
    }

    @GetMapping("/order/get-status")
    public ResponseEntity<Result> getAllStatus() {
        Result result = orderService.getAllStatus();
        return ResponseEntity.status(result.isSuccess() ? 200 : 409).body(result);
    }

}
