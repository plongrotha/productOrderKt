package org.plongrotha.productorder.model

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "products")
data class Product(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val productId: Long = 0,

    @Column(name = "product_name") var productName: String? = "",

    @Column(name = "description") var description: String? = "",

    @Column(name = "price") var price: BigDecimal?,

    @Column(name = "stockQuantity") var stockQuantity: Int,

    var imageUrl: String? = "",

    var createdAt: LocalDateTime? = LocalDateTime.now(),

    var updatedAt: LocalDateTime? = LocalDateTime.now()
) {
    @PrePersist
    fun onCreate(): Unit {
        createdAt = LocalDateTime.now()
    }

    @PreUpdate
    fun onUpdate(): Unit {
        updatedAt = LocalDateTime.now()
    }
}
