package org.plongrotha.productorder.repository

import org.plongrotha.productorder.model.Customer
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository


@Repository
interface CustomerRepository : JpaRepository<Customer, Long> {

    @Query("SELECT c FROM Customer c")
    fun findAllPagination(pageable: Pageable): Page<Customer>
}