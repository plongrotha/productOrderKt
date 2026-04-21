package org.plongrotha.productorder.dto.req

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.*
import java.math.BigDecimal

data class ProductRequest(
    @field:Schema(
        description = "The official name of the product", example = "IPhone 17 Pro"
    ) @field:NotBlank(message = "Product name is required") val productName: String?,

    @field:Schema(
        description = "The unit price of the product in USD", example = "999.99"
    ) @field:NotNull(message = "Price is required") @field:Positive(message = "Price must be greater than 0") var price: BigDecimal?,

    @field:Schema(
        description = "A detailed summary of product features", example = "this is description"
    ) @field:Size(
        min = 5, max = 255, message = "Description must be between 5 and 255 characters"
    ) val description: String?,

    @field:Schema(
        description = "Current number of items available in the warehouse", example = "50"
    ) @field:NotNull(message = "Stock is required") @field:Min(
        value = 0, message = "Stock cannot be negative"
    ) var stockQuantity: Int,


    @field:Schema(
        description = "image url", example = "https://i.pinimg.com/1200x/85/15/da/8515da71991d24a0668c65186850b143.jpg"
    ) var imageUrl: String?,
)