package org.plongrotha.productorder.service

import org.plongrotha.productorder.dto.req.ProductPriceUpdate
import org.plongrotha.productorder.dto.req.ProductRequest
import org.plongrotha.productorder.dto.res.PaginationResponse
import org.plongrotha.productorder.dto.res.ProductResponse
import org.springframework.data.domain.Pageable

interface ProductService {

    fun createProduct(productRequest: ProductRequest): ProductResponse

    fun updateProduct(id: Long, request: ProductRequest): ProductResponse

    fun getAllProduct(): List<ProductResponse>

    fun deleteProductById(id: Long): Unit

    fun getById(id: Long): ProductResponse

    fun createBolkProduct(products: List<ProductRequest>): List<ProductResponse>

    fun getProductPagination(page: Pageable): PaginationResponse<ProductResponse>

    fun updatePrice(id: Long, priceUpdate: ProductPriceUpdate): ProductResponse
}