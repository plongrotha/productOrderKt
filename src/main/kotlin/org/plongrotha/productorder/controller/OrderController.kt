package org.plongrotha.productorder.controller

import io.swagger.v3.oas.annotations.Operation
import org.plongrotha.productorder.dto.req.OrderRequest
import org.plongrotha.productorder.service.OrderService
import org.plongrotha.productorder.util.success
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/orders")
class OrderController(private val orderService: OrderService) {

    @Operation(summary = "Create a order")
    @PostMapping("/createNewOrder")
    fun createOrder(@RequestBody orderRequest: OrderRequest): Any {
        orderService.createOrder(orderRequest)
        return success(data = null, message = "Order created successfully")
    }

    @Operation(summary = "Get all order")
    @GetMapping
    fun getAllOrders() = success(data = orderService.getAllOrders())

}