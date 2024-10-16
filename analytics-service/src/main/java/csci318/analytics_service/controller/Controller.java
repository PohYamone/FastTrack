package csci318.analytics_service.controller;

import csci318.analytics_service.stream.OrderInteractiveQuery;
import csci318.analytics_service.model.UserOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class Controller {

    @Autowired
    private OrderInteractiveQuery userOrderInteractiveQuery;

    @GetMapping("/active")
    public List<UserOrder> getAllActiveOrders() {
        return userOrderInteractiveQuery.getAllActiveOrders();
    }

    @GetMapping("/active/{userId}")
    public UserOrder getActiveOrderCount(@PathVariable Long userId) {
        return userOrderInteractiveQuery.getActiveOrderCount(userId);
    }

    
}
