package org.plongrotha.productorder.dto.req

import java.time.LocalDate

data class CustomerRequest(
    val firstName: String?,
    val lastName: String?,
    val dob: LocalDate?,
    val email: String?,
    val phone: String?,
)
