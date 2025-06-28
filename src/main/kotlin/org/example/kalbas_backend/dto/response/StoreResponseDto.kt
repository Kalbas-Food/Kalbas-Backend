package org.example.kalbas_backend.dto.response

import java.time.LocalDateTime
import java.time.LocalTime

// Nested DTO for owner info
data class StoreOwnerDto(
    val id: Long?,
    val username: String,
    val email: String
)

// Main Store response DTO
data class StoreResponseDto(
    val id: Long?,
    val owner: StoreOwnerDto,
    val name: String,
    val description: String?,
    val address: String,
    val phoneNumber: String?,
    val isActive: Boolean,
    val isOpen: Boolean,
    val openingHours: LocalTime,
    val closingHours: LocalTime,
    val iconImageUrl: String?,
    val coverImageUrl: String?,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?
) 