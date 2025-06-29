package org.example.kalbas_backend.service

import org.example.kalbas_backend.dto.request.ProductCreateRequestDto
import org.example.kalbas_backend.dto.request.ProductUpdateRequestDto
import org.example.kalbas_backend.dto.response.ProductResponseDto
import org.example.kalbas_backend.dto.response.ProductStoreDto
import org.example.kalbas_backend.model.Product
import org.example.kalbas_backend.model.Store
import org.example.kalbas_backend.repository.ProductRepository
import org.example.kalbas_backend.repository.StoreRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

interface ProductService {
    fun createProduct(request: ProductCreateRequestDto): ProductResponseDto
    fun getProductById(id: Long): ProductResponseDto
    fun getAllProducts(): List<ProductResponseDto>
    fun getProductsByStore(storeId: Long): List<ProductResponseDto>
    fun updateProduct(id: Long, request: ProductUpdateRequestDto): ProductResponseDto
    fun deleteProduct(id: Long)
}

@Service
class ProductServiceImpl(
    private val productRepository: ProductRepository,
    private val storeRepository: StoreRepository
) : ProductService {

    @Transactional
    override fun createProduct(request: ProductCreateRequestDto): ProductResponseDto {
        val store: Store = storeRepository.findById(request.storeId)
            .orElseThrow { NoSuchElementException("Store not found with id: ${request.storeId}") }
        val product = Product(
            store = store,
            name = request.name,
            imageUrl = request.imageUrl,
            description = request.description,
            cost = request.cost
        )
        val saved = productRepository.save(product)
        return saved.toDto()
    }

    @Transactional(readOnly = true)
    override fun getProductById(id: Long): ProductResponseDto {
        val product = productRepository.findById(id)
            .orElseThrow { NoSuchElementException("Product not found with id: $id") }
        return product.toDto()
    }

    @Transactional(readOnly = true)
    override fun getAllProducts(): List<ProductResponseDto> =
        productRepository.findAll().map { it.toDto() }

    @Transactional(readOnly = true)
    override fun getProductsByStore(storeId: Long): List<ProductResponseDto> =
        productRepository.findByStoreId(storeId).map { it.toDto() }

    @Transactional
    override fun updateProduct(id: Long, request: ProductUpdateRequestDto): ProductResponseDto {
        val product = productRepository.findById(id)
            .orElseThrow { NoSuchElementException("Product not found with id: $id") }
        product.name = request.name
        product.imageUrl = request.imageUrl
        product.description = request.description
        product.cost = request.cost
        product.isActive = request.isActive
        val updated = productRepository.save(product)
        return updated.toDto()
    }

    @Transactional
    override fun deleteProduct(id: Long) {
        if (!productRepository.existsById(id)) {
            throw NoSuchElementException("Product not found with id: $id")
        }
        productRepository.deleteById(id)
    }

    private fun Product.toDto(): ProductResponseDto = ProductResponseDto(
        id = this.id,
        store = ProductStoreDto(
            id = this.store.id,
            name = this.store.name
        ),
        name = this.name,
        imageUrl = this.imageUrl,
        description = this.description,
        cost = this.cost,
        isActive = this.isActive,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    )
} 