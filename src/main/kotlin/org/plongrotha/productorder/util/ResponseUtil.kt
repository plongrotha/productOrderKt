package org.plongrotha.productorder.util

import org.plongrotha.productorder.dto.res.ApiResponse
import org.plongrotha.productorder.dto.res.PaginationResponse
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity


fun <T> success(data: T?, message: String = "Success"): ResponseEntity<ApiResponse<T>> {
    val body = ApiResponse(success = true, message = message, data = data)
    return ResponseEntity.ok(body)
}

fun delete(message: String = "Deleted successfully"): ResponseEntity<ApiResponse<Nothing>> {
    val body = ApiResponse<Nothing>(success = true, message = message)
    return ResponseEntity.ok(body)
}

fun error(message: String, status: HttpStatus): ResponseEntity<ApiResponse<Nothing>> {
    val body = ApiResponse<Nothing>(success = false, message = message)
    return ResponseEntity.status(status).body(body)
}

fun <T : Any, R : Any> Page<T>.toPaginationResponse(mapper: (T) -> R): PaginationResponse<R> {
    return PaginationResponse(
        items = this.content.map(mapper),
        page = this.number,
        pageSize = this.size,
        totalItems = this.totalElements,
        totalPages = this.totalPages,
        isFirst = this.isFirst,
        isLast = this.isLast,
        hasNext = this.hasNext(),
        hasPre = this.hasPrevious()
    )
}