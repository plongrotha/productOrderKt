package org.plongrotha.productorder.dto.res

import com.fasterxml.jackson.annotation.JsonInclude
import java.time.Instant

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ApiResponse<T>(
    val success: Boolean,
    val message: String,
    val data: T? = null,
    val timestamp: String = Instant.now().toString()
)

data class ApiErrorResponse(
    val status: Int,
    val message: String,
    val path: String,
    val timestamp: String = java.time.LocalDateTime.now().toString()
)