package org.plongrotha.productorder.dto.res

import org.plongrotha.productorder.model.Customer
import java.time.LocalDate
import java.time.LocalDateTime

data class CustomerResponse(
    val id: Long,
    val firstName: String?,
    val lastname: String?,
    val email: String?,
    val phone: String?,
    val dateOfBirth: LocalDate?,
    val age: Int?,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?
)

fun Customer.toResponse(): CustomerResponse {
    return CustomerResponse(
        id = customerId,
        firstName = firstName,
        lastname = lastName,
        phone = phone,
        email = email,
        dateOfBirth = dateOfBirth,
        age = age,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}
