package org.plongrotha.productorder

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ProductOrderApplication

fun main(args: Array<String>) {
    runApplication<ProductOrderApplication>(*args)
}
