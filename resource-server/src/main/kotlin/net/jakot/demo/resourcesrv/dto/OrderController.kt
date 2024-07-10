package net.jakot.demo.resourcesrv.dto

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/orders")
class OrderController {

    @GetMapping
    fun getOrders(): List<Order> {
        return listOf(
            Order("asd123", "Order 1", 125.0),
            Order("qwe234", "Order 2", 250.50),
            Order("zxc345", "Order 3", 499.99)
        )
    }
}