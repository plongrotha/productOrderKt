package org.plongrotha.productorder.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Positive
import org.plongrotha.productorder.dto.req.ProductPriceUpdate
import org.plongrotha.productorder.dto.req.ProductRequest
import org.plongrotha.productorder.dto.res.ApiResponse
import org.plongrotha.productorder.dto.res.PaginationResponse
import org.plongrotha.productorder.dto.res.ProductResponse
import org.plongrotha.productorder.service.ProductService
import org.plongrotha.productorder.util.delete
import org.plongrotha.productorder.util.success
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/products")
class ProductController(private val productService: ProductService) {

    @PostMapping("AddNew")
    @Operation(summary = "Create a new product")
    fun createProduct(@RequestBody request: ProductRequest): ResponseEntity<ApiResponse<Unit>> {
        productService.createProduct(request)
        return success(data = null, message = "Product created successfully")
    }

    @GetMapping
    @Operation(summary = "Get a list of all products")
    fun getAllProducts() = success(
        data = productService.getAllProduct(), message = "All products retrieved successfully"
    )

    @GetMapping("/{id}")
    @Operation(summary = "Get product details by ID")
    fun getProductById(@PathVariable @Positive id: Long) = success(
        data = productService.getById(id), message = "Product details retrieved successfully"
    )

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing product")
    fun updateProduct(@PathVariable @Positive id: Long, @RequestBody request: ProductRequest) = success(
        data = productService.updateProduct(id, request), message = "Product updated successfully"
    )

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a product by ID")
    fun deleteProduct(@PathVariable @Positive id: Long): Unit {
        productService.deleteProductById(id)
        delete(message = "Product deleted successfully")
    }

    @Operation(
        summary = "Create multiple products",
        description = "This API allows creating multiple products in a single request (bulk insert). " + "It accepts a list of product objects and saves them to the database."
    )
    @PostMapping("/bulk")
    fun createBulkProduct(
        @RequestBody request: List<ProductRequest>
    ) = success(
        data = productService.createBolkProduct(request), message = "Bulk product creation completed successfully"
    )

    @Operation(
        summary = "Get products with pagination",
        description = "Retrieve a paginated list of products. You can control page number and page size using query parameters."
    )
    @GetMapping("/pagination")
    fun getAllProductWithPagination(
        @Parameter(description = "Page number (starts from 0)") @RequestParam(defaultValue = "0") @Min(
            0
        ) page: Int, @Parameter(
            description = "Number of records per page", example = "10"
        ) @RequestParam(defaultValue = "10") @Positive @Min(1) size: Int
    ): ResponseEntity<PaginationResponse<ProductResponse>> {

        val pageable = PageRequest.of(page, size)
        val response = productService.getProductPagination(pageable)

        return ResponseEntity.ok(response)
    }

    @Operation(summary = "Update product price")
    @PatchMapping("/{id}/price")
    fun updateProductPrice(@PathVariable id: Long, @RequestBody request: ProductPriceUpdate) =
        success(data = productService.updatePrice(id, request))
}