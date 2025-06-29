package org.example.kalbas_backend.dto.request

import jakarta.validation.constraints.*

// DTO for creating a Category
data class CategoryCreateRequestDto(
    @field:NotBlank(message = "Name cannot be blank")
    @field:Size(max = 255, message = "Name must not exceed 255 characters")
    val name: String,

    val parentCategoryId: Long? = null,

    @field:Size(max = 2083, message = "Image URL must not exceed 2083 characters")
    val imageUrl: String? = null
)

// DTO for updating a Category
data class CategoryUpdateRequestDto(
    @field:NotBlank(message = "Name cannot be blank")
    @field:Size(max = 255, message = "Name must not exceed 255 characters")
    val name: String,

    val parentCategoryId: Long? = null,

    @field:NotNull(message = "Is active cannot be null")
    val isActive: Boolean,

    @field:Size(max = 2083, message = "Image URL must not exceed 2083 characters")
    val imageUrl: String? = null
) 