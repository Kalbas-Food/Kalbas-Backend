package org.example.kalbas_backend.model

import jakarta.persistence.*
import jakarta.validation.constraints.*

import java.time.LocalTime

@Entity
@Table(name = "stores")
class Store(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @field:NotNull(message = "Owner ID cannot be null")
    @Column(name = "owner_id", nullable = false)
    var ownerId: Long = 0L

    @field:NotBlank(message = "Name cannot be blank")
    @field:Size(max = 255, message = "Name must not exceed 255 characters")
    var name: String = "",

    @field:Size(max = 1000, message = "Description must not exceed 1000 characters")
    var description: String = "",

    @field:NotBlank(message = "Address cannot be blank")
    @field:Size(max = 500, message = "Address must not exceed 500 characters")
    var address: String = "",

    @field:Pattern(
        regexp = "\\+?[0-9. ()-]{7,25}",
        message = "Phone number is invalid"
    )
    var phoneNumber: String = "",

    var isActive: Boolean = true,
    var isOpen: Boolean = true,

    @field:NotNull(message = "Opening hours cannot be null")
    var openingHours: LocalTime = LocalTime.of(8, 0),

    @field:NotNull(message = "Closing hours cannot be null")
    var closingHours: LocalTime = LocalTime.MIDNIGHT,

    @field:Size(max = 2083, message = "Icon image URL must not exceed 2083 characters")
    var iconImageUrl: String = "",

    @field:Size(max = 2083, message = "Cover image URL must not exceed 2083 characters")
    var coverImageUrl: String = ""
) {
    constructor() : this(
        id = null,
        ownerId = 0L,
        name = "",
        description = "",
        address = "",
        phoneNumber = "",
        isActive = true,
        isOpen = true,
        openingHours = LocalTime.of(8, 0),
        closingHours = LocalTime.MIDNIGHT,
        iconImageUrl = "",
        coverImageUrl = ""
    )
}