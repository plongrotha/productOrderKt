package org.plongrotha.productorder.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.PrePersist
import jakarta.persistence.PreUpdate
import jakarta.persistence.Table
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "customers")
data class Customer(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val customerId: Long = 0,

    var firstName: String?,

    var lastName: String?,

    var dateOfBirth: LocalDate?,

    var age: Int? = null,

    var phone: String?,

    var email: String?,

    var createdAt: LocalDateTime? = null,

    var updatedAt: LocalDateTime? = null,

    @JsonIgnore
    @OneToMany(mappedBy = "customer", orphanRemoval = true, cascade = [CascadeType.ALL])
    var order: MutableList<Order> = mutableListOf()

) {
    @PrePersist
    fun onCreate() {
        val now = LocalDateTime.now()
        createdAt = now
        updatedAt = now
    }

    @PreUpdate
    fun onUpdate() {
        updatedAt = LocalDateTime.now()
    }
}