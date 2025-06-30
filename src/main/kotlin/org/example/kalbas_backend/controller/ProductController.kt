package org.example.kalbas_backend.controller

import org.example.kalbas_backend.dto.request.ProductCreateRequestDto
import org.example.kalbas_backend.dto.request.ProductUpdateRequestDto
import org.example.kalbas_backend.dto.response.ProductResponseDto
import org.example.kalbas_backend.dto.response.MessageResponseDto
import org.example.kalbas_backend.dto.response.ApiResponse
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
    fun createProduct(@Validated @RequestBody request: ProductCreateRequestDto): ResponseEntity<ApiResponse<ProductResponseDto>> =
        ResponseEntity.ok(
            ApiResponse(
                success = true,
                message = "Product created successfully",
                data = productService.createProduct(request)
            )
        )

    @GetMapping("/{id}")
    fun getProductById(@PathVariable id: Long): ResponseEntity<ApiResponse<ProductResponseDto>> =
        ResponseEntity.ok(
            ApiResponse(
                success = true,
                message = "Product retrieved successfully",
                data = productService.getProductById(id)
            )
        )

    @GetMapping
    fun getAllProducts(): ResponseEntity<ApiResponse<List<ProductResponseDto>>> =
        ResponseEntity.ok(
            ApiResponse(
                success = true,
                message = "Products retrieved successfully",
                data = productService.getAllProducts()
            )
        )

    @GetMapping("/store/{storeId}")
    fun getProductsByStore(@PathVariable storeId: Long): ResponseEntity<ApiResponse<List<ProductResponseDto>>> =
        ResponseEntity.ok(
            ApiResponse(
                success = true,
                message = "Products for store retrieved successfully",
                data = productService.getProductsByStore(storeId)
            )
        )

    @PutMapping("/{id}")
    fun updateProduct(
        @PathVariable id: Long,
        @Validated @RequestBody request: ProductUpdateRequestDto
    ): ResponseEntity<ApiResponse<ProductResponseDto>> =
        ResponseEntity.ok(
            ApiResponse(
                success = true,
                message = "Product updated successfully",
                data = productService.updateProduct(id, request)
            )
        )

    @DeleteMapping("/{id}")
    fun deleteProduct(@PathVariable id: Long): ResponseEntity<ApiResponse<MessageResponseDto>> {
        productService.deleteProduct(id)
        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                message = "Product deleted successfully",
                data = MessageResponseDto("Product deleted successfully")
            )
        )
    }
} 