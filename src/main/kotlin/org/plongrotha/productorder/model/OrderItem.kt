package org.plongrotha.productorder.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.math.BigDecimal

@Entity
@Table(name = "order_items")
data class OrderItem(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val orderItemId: Long? = null,

    var quantity: Int = 0,

    var unitPrice: BigDecimal?,

    @ManyToOne @JoinColumn(name = "orderId") var order: Order? = null,

    @ManyToOne @JoinColumn(name = "productId") var product: Product? = null
)
