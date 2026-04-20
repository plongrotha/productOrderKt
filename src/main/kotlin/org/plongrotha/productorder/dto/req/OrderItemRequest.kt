package org.plongrotha.productorder.dto.req

data class OrderItemRequest(
    val productId: Long,
    val qty: Int
)
