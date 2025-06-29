package org.example.kalbas_backend.dto.response

import java.time.LocalDateTime

// Nested DTO for parent category info
data class CategoryParentDto(
    val id: Long?,
    val name: String,
    val imageUrl: String?
)

// Main Category response DTO
data class CategoryResponseDto(
    val id: Long?,
    val name: String,
    val imageUrl: String?,
    val parentCategory: CategoryParentDto?,
    val isActive: Boolean,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?
) 