package org.plongrotha.productorder.service.impl

import org.plongrotha.productorder.dto.req.CustomerRequest
import org.plongrotha.productorder.dto.res.CustomerResponse
import org.plongrotha.productorder.dto.res.PaginationResponse
import org.plongrotha.productorder.dto.res.toResponse
import org.plongrotha.productorder.exception.ResourceNotFoundException
import org.plongrotha.productorder.model.Customer
import org.plongrotha.productorder.repository.CustomerRepository
import org.plongrotha.productorder.service.CustomerService
import org.plongrotha.productorder.util.toPaginationResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.Period


@Service
class CustomerServiceImpl(
    private val customerRepo: CustomerRepository,
    private val log: Logger = LoggerFactory.getLogger(CustomerService::class.java)
) : CustomerService {

    override fun getCustomerById(id: Long): CustomerResponse? {
        return customerRepo.findByIdOrNull(id)?.toResponse()
            ?: throw ResourceNotFoundException("not found customer with id $id")
    }

    override fun getCustomersPagination(pageable: Pageable): PaginationResponse<CustomerResponse> {
        return customerRepo.findAllPagination(pageable).toPaginationResponse { it.toResponse() }
    }

    override fun updateCustomer(
        id: Long, request: CustomerRequest
    ): CustomerResponse {

        val customer =
            customerRepo.findByIdOrNull(id) ?: throw ResourceNotFoundException("customer with id $id is not found")

        val updatedCustomer = customer.copy(
            firstName = request.firstName,
            lastName = request.lastName,
            email = request.email,
            phone = request.phone,
            dateOfBirth = request.dob,
            age = Period.between(request.dob, LocalDate.now()).years
        )

        return customerRepo.save(updatedCustomer).toResponse()
    }

    override fun deleteCustomer(id: Long) {
        val customer =
            customerRepo.findByIdOrNull(id) ?: throw ResourceNotFoundException("customer with id $id is not found")
        customerRepo.delete(customer)
    }

    override fun createCustomer(request: CustomerRequest) {
//        val newCustomer = Customer(
//            firstName = request.firstName,
//            lastName = request.lastName,
//            dateOfBirth = request.dob,
//            age = request.dob?.let { Period.between(it, LocalDate.now()).years },
//            email = request.email,
//            phone = request.phone
//        )

        val newCustomerV2 = request.let { it ->
            Customer(
                firstName = it.firstName,
                lastName = it.lastName,
                email = it.email,
                phone = it.phone,
                dateOfBirth = it.dob,
                age = it.dob?.let { Period.between(it, LocalDate.now()).years },
            )
        }
        customerRepo.save(newCustomerV2)
    }

    override fun getAllCustomer(): List<CustomerResponse> {
        return customerRepo.findAll().map { it.toResponse() }
    }

    override fun createCustomerBulk(request: List<CustomerRequest>): List<CustomerResponse> {

        val customerList = request.map { request ->
            Customer(
                firstName = request.firstName,
                lastName = request.lastName,
                dateOfBirth = request.dob,
                age = Period.between(request.dob, LocalDate.now()).years,
                email = request.email,
                phone = request.phone
            )
        }
        return customerRepo.saveAll(customerList).map { it.toResponse() }
    }
}