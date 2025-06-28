package org.example.kalbas_backend.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import jakarta.validation.constraints.Pattern
import java.time.LocalTime

// DTO for creating a Store
data class StoreCreateRequestDto(
    @field:NotNull(message = "Owner ID cannot be null")
    val ownerId: Long,

    @field:NotBlank(message = "Name cannot be blank")
    @field:Size(max = 255, message = "Name must not exceed 255 characters")
    val name: String,

    @field:Size(max = 1000, message = "Description must not exceed 1000 characters")
    val description: String? = null,

    @field:NotBlank(message = "Address cannot be blank")
    @field:Size(max = 500, message = "Address must not exceed 500 characters")
    val address: String,

    @field:Pattern(
        regexp = "\\+?[0-9. ()-]{7,25}",
        message = "Phone number is invalid"
    )
    val phoneNumber: String? = null,

    @field:NotNull(message = "Opening hours cannot be null")
    val openingHours: LocalTime,

    @field:NotNull(message = "Closing hours cannot be null")
    val closingHours: LocalTime,

    @field:Size(max = 2083, message = "Icon image URL must not exceed 2083 characters")
    val iconImageUrl: String? = null,

    @field:Size(max = 2083, message = "Cover image URL must not exceed 2083 characters")
    val coverImageUrl: String? = null
)

// DTO for updating a Store
data class StoreUpdateRequestDto(
    @field:NotBlank(message = "Name cannot be blank")
    @field:Size(max = 255, message = "Name must not exceed 255 characters")
    val name: String,

    @field:Size(max = 1000, message = "Description must not exceed 1000 characters")
    val description: String? = null,

    @field:NotBlank(message = "Address cannot be blank")
    @field:Size(max = 500, message = "Address must not exceed 500 characters")
    val address: String,

    @field:Pattern(
        regexp = "\\+?[0-9. ()-]{7,25}",
        message = "Phone number is invalid"
    )
    val phoneNumber: String? = null,

    @field:NotNull(message = "Opening hours cannot be null")
    val openingHours: LocalTime,

    @field:NotNull(message = "Closing hours cannot be null")
    val closingHours: LocalTime,

    @field:Size(max = 2083, message = "Icon image URL must not exceed 2083 characters")
    val iconImageUrl: String? = null,

    @field:Size(max = 2083, message = "Cover image URL must not exceed 2083 characters")
    val coverImageUrl: String? = null,

    @field:NotNull(message = "Is active cannot be null")
    val isActive: Boolean,

    @field:NotNull(message = "Is open cannot be null")
    val isOpen: Boolean
) 