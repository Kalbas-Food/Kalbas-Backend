package org.example.kalbas_backend.model

import jakarta.persistence.*
import jakarta.validation.constraints.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "orders")
class Order(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @field:NotNull(message = "User cannot be null")
    var user: User,

    @Column(name = "status", length = 50, nullable = false)
    @field:NotBlank(message = "Status cannot be blank")
    @field:Size(max = 50, message = "Status must not exceed 50 characters")
    var status: String = "PENDING",

    @Column(name = "total_amount", nullable = false)
    @field:NotNull(message = "Total amount cannot be null")
    @field:DecimalMin(value = "0.0", inclusive = true, message = "Total amount must be at least 0")
    var totalAmount: BigDecimal = BigDecimal.ZERO,

    @Column(name = "is_paid", nullable = false)
    var isPaid: Boolean = false,

    @Column(name = "is_active", nullable = false)
    var isActive: Boolean = true,

    @CreationTimestamp
    @Column(updatable = false)
    var createdAt: LocalDateTime? = null,

    @UpdateTimestamp
    var updatedAt: LocalDateTime? = null
) 