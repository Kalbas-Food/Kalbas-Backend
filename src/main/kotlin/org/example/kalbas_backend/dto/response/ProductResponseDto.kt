package org.example.kalbas_backend.dto.response

import java.math.BigDecimal
import java.time.LocalDateTime

// Nested DTO for store info
data class ProductStoreDto(
    val id: Long?,
    val name: String
)

// Main Product response DTO
data class ProductResponseDto(
    val id: Long?,
    val store: ProductStoreDto,
    val name: String,
    val imageUrl: String?,
    val description: String?,
    val cost: BigDecimal,
    val isActive: Boolean,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?
) 