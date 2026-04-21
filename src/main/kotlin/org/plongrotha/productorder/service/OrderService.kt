package org.plongrotha.productorder.service

import org.plongrotha.productorder.dto.req.OrderRequest
import org.plongrotha.productorder.dto.res.OrderResponse

interface OrderService {


    fun createOrder(orderRequest: OrderRequest): Unit

    fun createOrderV2(orderRequest: OrderRequest): OrderResponse

    fun getAllOrders(): List<OrderResponse>

}