package org.example.kalbas_backend.dto.request

import jakarta.validation.constraints.*

// DTO for creating a Category
data class CategoryCreateRequestDto(
    @field:NotBlank(message = "Name cannot be blank")
    @field:Size(max = 255, message = "Name must not exceed 255 characters")
    val name: String,

    val parentCategoryId: Long? = null
)

// DTO for updating a Category
data class CategoryUpdateRequestDto(
    @field:NotBlank(message = "Name cannot be blank")
    @field:Size(max = 255, message = "Name must not exceed 255 characters")
    val name: String,

    val parentCategoryId: Long? = null,

    @field:NotNull(message = "Is active cannot be null")
    val isActive: Boolean
) 