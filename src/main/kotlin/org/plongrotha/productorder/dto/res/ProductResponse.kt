package org.plongrotha.productorder.dto.res

import org.plongrotha.productorder.model.Product
import java.math.BigDecimal

data class ProductResponse(
    val productId: Long?,
    val productName: String?,
    val description: String?,
    val price: BigDecimal?,
    val imageUrl: String?,
    val stockQuantity: Int?,
    val isAvailable: Boolean = stockQuantity!! > 0
)

fun Product.toResponse(): ProductResponse {
    return ProductResponse(
        productId = productId,
        productName = productName,
        description = description,
        imageUrl = imageUrl,
        price = price,
        stockQuantity = stockQuantity
    )
}
