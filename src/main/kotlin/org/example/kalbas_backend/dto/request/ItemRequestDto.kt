package org.example.kalbas_backend.dto.request

import jakarta.validation.constraints.*
import java.math.BigDecimal

// DTO for creating an Item
data class ItemCreateRequestDto(
    @field:NotNull(message = "Store ID cannot be null")
    val storeId: Long,

    @field:NotBlank(message = "Name cannot be blank")
    @field:Size(max = 255, message = "Name must not exceed 255 characters")
    val name: String,

    @field:Size(max = 2083, message = "Image URL must not exceed 2083 characters")
    val imageUrl: String? = null,

    @field:Size(max = 1000, message = "Description must not exceed 1000 characters")
    val description: String? = null,

    @field:NotNull(message = "Cost cannot be null")
    @field:DecimalMin(value = "0.0", inclusive = false, message = "Cost must be greater than 0")
    val cost: BigDecimal
)

// DTO for updating an Item
data class ItemUpdateRequestDto(
    @field:NotBlank(message = "Name cannot be blank")
    @field:Size(max = 255, message = "Name must not exceed 255 characters")
    val name: String,

    @field:Size(max = 2083, message = "Image URL must not exceed 2083 characters")
    val imageUrl: String? = null,

    @field:Size(max = 1000, message = "Description must not exceed 1000 characters")
    val description: String? = null,

    @field:NotNull(message = "Cost cannot be null")
    @field:DecimalMin(value = "0.0", inclusive = false, message = "Cost must be greater than 0")
    val cost: BigDecimal,

    @field:NotNull(message = "Is active cannot be null")
    val isActive: Boolean
) 