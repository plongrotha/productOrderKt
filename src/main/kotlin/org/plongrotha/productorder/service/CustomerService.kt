package org.plongrotha.productorder.service

import org.plongrotha.productorder.dto.req.CustomerRequest
import org.plongrotha.productorder.dto.res.CustomerResponse
import org.plongrotha.productorder.dto.res.PaginationResponse
import org.springframework.data.domain.Pageable

interface CustomerService {

    fun getCustomerById(id: Long): CustomerResponse?

    fun getCustomersPagination(pageable: Pageable): PaginationResponse<CustomerResponse>

    fun updateCustomer(id: Long, request: CustomerRequest): CustomerResponse

    fun deleteCustomer(id: Long): Unit

    fun createCustomer(request: CustomerRequest): Unit

    fun getAllCustomer(): List<CustomerResponse>

    fun createCustomerBulk(request: List<CustomerRequest>): List<CustomerResponse>

}