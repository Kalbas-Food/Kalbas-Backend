package org.example.kalbas_backend.model

import jakarta.persistence.*
import jakarta.validation.constraints.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "products")
class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "store_id", nullable = false)
    @field:NotNull(message = "Store cannot be null")
    var store: Store,

    @Column(name = "name", length = 255, nullable = false)
    @field:NotBlank(message = "Name cannot be blank")
    @field:Size(max = 255, message = "Name must not exceed 255 characters")
    var name: String,

    @Column(name = "image_url", length = 2083)
    @field:Size(max = 2083, message = "Image URL must not exceed 2083 characters")
    var imageUrl: String? = null,

    @Column(name = "description", length = 1000)
    @field:Size(max = 1000, message = "Description must not exceed 1000 characters")
    var description: String? = null,

    @Column(name = "cost", nullable = false)
    @field:NotNull(message = "Cost cannot be null")
    @field:DecimalMin(value = "0.0", inclusive = false, message = "Cost must be greater than 0")
    var cost: BigDecimal,

    @Column(name = "is_active", nullable = false)
    var isActive: Boolean = true,

    @CreationTimestamp
    @Column(updatable = false)
    var createdAt: LocalDateTime? = null,

    @UpdateTimestamp
    var updatedAt: LocalDateTime? = null
) 