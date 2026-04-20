package org.plongrotha.productorder.service.impl

import org.plongrotha.productorder.dto.req.ProductPriceUpdate
import org.plongrotha.productorder.dto.req.ProductRequest
import org.plongrotha.productorder.dto.res.PaginationResponse
import org.plongrotha.productorder.dto.res.ProductResponse
import org.plongrotha.productorder.dto.res.toResponse
import org.plongrotha.productorder.exception.ResourceNotFoundException
import org.plongrotha.productorder.model.Product
import org.plongrotha.productorder.repository.ProductRepository
import org.plongrotha.productorder.service.ProductService
import org.plongrotha.productorder.util.toPaginationResponse
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class ProductServiceImpl(
    private val productRepository: ProductRepository
) : ProductService {

    override fun createProduct(productRequest: ProductRequest): ProductResponse {
        val newProduct = Product(
            productName = productRequest.productName,
            description = productRequest.description,
            price = productRequest.price,
            imageUrl = productRequest.imageUrl,
            stockQuantity = productRequest.stockQuantity
        )
        return productRepository.save(newProduct).toResponse()
    }

    override fun updateProduct(id: Long, request: ProductRequest): ProductResponse {
        val product =
            productRepository.findById(id).orElseThrow { ResourceNotFoundException("Product with id $id is not found") }

        val productUpdate = product.copy(
            productName = request.productName,
            description = request.description,
            price = request.price,
            imageUrl = request.imageUrl,
            stockQuantity = request.stockQuantity
        )
        
        return productRepository.save(productUpdate).toResponse()
    }

    override fun getAllProduct(): List<ProductResponse> {
        return productRepository.findAll().map { it.toResponse() }
    }

    override fun deleteProductById(id: Long) {
        val product =
            productRepository.findById(id).orElseThrow { ResourceNotFoundException("Product with id $id is not found") }
        productRepository.delete(product)
    }

    override fun getById(id: Long): ProductResponse {
        return productRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Product with id $id is not found") }.toResponse()
    }

    override fun createBolkProduct(products: List<ProductRequest>): List<ProductResponse> {
        val productList = products.map { request ->
            Product(
                productName = request.productName,
                description = request.description,
                price = request.price,
                imageUrl = request.imageUrl,
                stockQuantity = request.stockQuantity
            )
        }
        val savedAllProduct = productRepository.saveAll(productList)
        return savedAllProduct.map { it.toResponse() }
    }

    override fun getProductPagination(page: Pageable): PaginationResponse<ProductResponse> {
        return productRepository.findAllPagination(page).toPaginationResponse {
            it.toResponse()
        }
    }
    
    override fun updatePrice(id: Long, priceUpdate: ProductPriceUpdate): ProductResponse {
        val product =
            productRepository.findById(id).orElseThrow { ResourceNotFoundException("Product with id $id is not found") }
        val updatePrice = product.copy(price = priceUpdate.price)
        return productRepository.save(updatePrice).toResponse()
    }
}