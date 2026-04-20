package org.plongrotha.productorder.dto.res

data class PaginationResponse<T>(
    var items: List<T>,
    val page: Int,
    val totalPages: Int,
    val totalItems: Long,
    val pageSize: Int,
    val hasNext: Boolean,
    val isFirst: Boolean,
    val isLast: Boolean
)
