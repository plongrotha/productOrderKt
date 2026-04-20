package org.plongrotha.productorder.service.impl

import lombok.extern.slf4j.Slf4j
import org.plongrotha.productorder.dto.req.OrderRequest
import org.plongrotha.productorder.dto.res.OrderResponse
import org.plongrotha.productorder.dto.res.toResponse
import org.plongrotha.productorder.exception.ResourceNotFoundException
import org.plongrotha.productorder.model.Order
import org.plongrotha.productorder.model.OrderItem
import org.plongrotha.productorder.repository.CustomerRepository
import org.plongrotha.productorder.repository.OrderItemRepository
import org.plongrotha.productorder.repository.OrderRepository
import org.plongrotha.productorder.repository.ProductRepository
import org.plongrotha.productorder.service.OrderService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

@Slf4j
@Service
class OrderServiceImpl(
    private val orderRepo: OrderRepository,
    private val customerRepo: CustomerRepository,
    private val productRepo: ProductRepository,
    private val orderItemRepo: OrderItemRepository,
) : OrderService {

    @Transactional
    override fun createOrder(orderRequest: OrderRequest) {
        val customer = customerRepo.findByIdOrNull(orderRequest.customerId)
            ?: throw ResourceNotFoundException("Customer with with id ${orderRequest.customerId} is not found")

        val order = orderRepo.save(Order(customer = customer, totalAmount = BigDecimal.ZERO))

        var totalAmount = BigDecimal.ZERO

        val orderItem = orderRequest.items.map { itemRequest ->

            val product = productRepo.findByIdOrNull(itemRequest.productId)
                ?: throw ResourceNotFoundException("Product with Id : ${itemRequest.productId} is not found.")

            product.stockQuantity?.let {
                if (it < itemRequest.qty) {
                    throw IllegalArgumentException("Not enough stock for product ${product.productName}, In stock is ${product.stockQuantity} but order qty is ${itemRequest.qty}")
                }
            }

            product.stockQuantity = product.stockQuantity?.minus(itemRequest.qty)
            productRepo.save(product)

            val totalItem = product.price?.multiply(BigDecimal(itemRequest.qty))

            totalAmount = totalAmount.add(totalItem)

            OrderItem(
                order = order, product = product, quantity = itemRequest.qty, unitPrice = totalItem,
            )
        }

        orderItemRepo.saveAll(orderItem)

        order.totalAmount = totalAmount

        orderRepo.save(order)
    }

    override fun getAllOrders(): List<OrderResponse> {
        return orderRepo.findAll().map { it.toResponse() }
    }
}
