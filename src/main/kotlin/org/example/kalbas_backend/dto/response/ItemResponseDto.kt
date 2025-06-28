package org.example.kalbas_backend.dto.response

import java.math.BigDecimal
import java.time.LocalDateTime

// Nested DTO for store info
data class ItemStoreDto(
    val id: Long?,
    val name: String
)

// Main Item response DTO
data class ItemResponseDto(
    val id: Long?,
    val store: ItemStoreDto,
    val name: String,
    val imageUrl: String?,
    val description: String?,
    val cost: BigDecimal,
    val isActive: Boolean,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?
)