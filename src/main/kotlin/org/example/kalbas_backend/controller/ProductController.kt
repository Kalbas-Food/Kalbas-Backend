package org.example.kalbas_backend.controller

import org.example.kalbas_backend.dto.request.ProductCreateRequestDto
import org.example.kalbas_backend.dto.request.ProductUpdateRequestDto
import org.example.kalbas_backend.dto.response.ProductResponseDto
import org.example.kalbas_backend.dto.response.MessageResponseDto
import org.example.kalbas_backend.service.ProductService
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/products")
class ProductController(
    private val productService: ProductService
) {
    @PostMapping
    fun createProduct(@Validated @RequestBody request: ProductCreateRequestDto): ResponseEntity<ProductResponseDto> =
        ResponseEntity.ok(productService.createProduct(request))

    @GetMapping("/{id}")
    fun getProductById(@PathVariable id: Long): ResponseEntity<ProductResponseDto> =
        ResponseEntity.ok(productService.getProductById(id))

    @GetMapping
    fun getAllProducts(): ResponseEntity<List<ProductResponseDto>> =
        ResponseEntity.ok(productService.getAllProducts())

    @GetMapping("/store/{storeId}")
    fun getProductsByStore(@PathVariable storeId: Long): ResponseEntity<List<ProductResponseDto>> =
        ResponseEntity.ok(productService.getProductsByStore(storeId))

    @PutMapping("/{id}")
    fun updateProduct(
        @PathVariable id: Long,
        @Validated @RequestBody request: ProductUpdateRequestDto
    ): ResponseEntity<ProductResponseDto> =
        ResponseEntity.ok(productService.updateProduct(id, request))

    @DeleteMapping("/{id}")
    fun deleteProduct(@PathVariable id: Long): ResponseEntity<MessageResponseDto> {
        productService.deleteProduct(id)
        return ResponseEntity.ok(MessageResponseDto("Product deleted successfully"))
    }
} 