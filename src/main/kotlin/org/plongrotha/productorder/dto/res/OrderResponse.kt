package org.plongrotha.productorder.dto.res

import org.plongrotha.productorder.model.Order
import org.plongrotha.productorder.model.OrderItem
import java.math.BigDecimal
import kotlin.Long

data class OrderResponse(
    val orderId: Long?,
    val customerId: Long?,
    val customerName: String?,
    val totalPrice: BigDecimal?,
    val items: List<OrderItemResponse>
)

data class OrderItemResponse(
    val productId: Long?, val quantity: Int?
)

fun OrderItem.toResponse(): OrderItemResponse {
    return OrderItemResponse(
        productId = product?.productId, quantity = quantity
    )
}

fun Order.toResponse(): OrderResponse {
    return OrderResponse(
        orderId = orderId,
        customerId = customer?.customerId,
        customerName = customer?.let { "${it.firstName} ${it.lastName}" },
        totalPrice = totalAmount,
        items = items.map { it.toResponse() })
}