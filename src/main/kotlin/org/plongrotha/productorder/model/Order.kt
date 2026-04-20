package org.plongrotha.productorder.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.PrePersist
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "orders")
data class Order(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val orderId: Long = 0,

    var orderDate: LocalDateTime = LocalDateTime.now(),

    var totalAmount: BigDecimal = BigDecimal.ZERO,

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "customerId") val customer: Customer? = null,

    @JsonIgnore
    @OneToMany(mappedBy = "order")
    val items: MutableList<OrderItem> = mutableListOf(),
) {
    @PrePersist
    fun onCreate() {
        orderDate = LocalDateTime.now()
    }
}
