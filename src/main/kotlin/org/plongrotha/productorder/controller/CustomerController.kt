package org.plongrotha.productorder.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Positive
import org.plongrotha.productorder.dto.req.CustomerRequest
import org.plongrotha.productorder.dto.res.ApiResponse
import org.plongrotha.productorder.dto.res.CustomerResponse
import org.plongrotha.productorder.dto.res.PaginationResponse
import org.plongrotha.productorder.service.CustomerService
import org.plongrotha.productorder.util.delete
import org.plongrotha.productorder.util.success
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/customers")
class CustomerController(private val customerService: CustomerService) {

    @PostMapping
    @Operation(summary = "Create a new customer")
    fun createCustomer(@RequestBody request: CustomerRequest): ResponseEntity<ApiResponse<Unit>> {
        customerService.createCustomer(request)
        return success(data = null, message = "Customer created successfully")
    }

    @GetMapping("/page")
    @Operation(summary = "Get a list of all customers with pagination")
    fun getCustomersPagination(
        @RequestParam(defaultValue = "0") page: Int, @RequestParam(defaultValue = "10") @Positive size: Int
    ): ResponseEntity<PaginationResponse<CustomerResponse>> {

        val pageable = PageRequest.of(page, size)
        val response = customerService.getCustomersPagination(pageable)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get customer details by ID")
    fun getCustomerById(@PathVariable @Positive id: Long) = success(
        data = customerService.getCustomerById(id), message = "Customer details retrieved successfully"
    )

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing customer")
    fun updateCustomer(
        @PathVariable @Positive id: Long, @RequestBody request: CustomerRequest
    ) = success(
        data = customerService.updateCustomer(id, request), message = "Customer updated successfully"
    )

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a customer by ID")
    fun deleteCustomer(@PathVariable @Positive @Parameter(description = "Customer Id") id: Long): ResponseEntity<ApiResponse<Nothing>> {
        customerService.deleteCustomer(id)
        return delete(message = "delete customer successfully")
    }

    @Operation(summary = "Get All customer")
    @GetMapping
    fun getAllCustomer() =
        success(data = customerService.getAllCustomer(), message = "All customer retrieve successfully")

    @PostMapping("/bulk")
    fun createBulkCustomer(@RequestBody request: List<CustomerRequest>) =
        success(data = customerService.createCustomerBulk(request), message = "Customer successfully created")
}