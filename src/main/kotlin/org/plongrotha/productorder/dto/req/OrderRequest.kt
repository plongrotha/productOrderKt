package org.plongrotha.productorder.dto.req

data class OrderRequest(
    val customerId: Long,
    val items: List<OrderItemRequest>,
)
