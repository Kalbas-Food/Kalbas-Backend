package org.example.kalbas_backend.model

import jakarta.persistence.*
import jakarta.validation.constraints.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "invoices")
class Invoice(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false, unique = true)
    @field:NotNull(message = "Order cannot be null")
    var order: Order,

    @Column(name = "invoice_number", length = 100, nullable = false, unique = true)
    @field:NotBlank(message = "Invoice number cannot be blank")
    @field:Size(max = 100, message = "Invoice number must not exceed 100 characters")
    var invoiceNumber: String,

    @Column(name = "total_amount", nullable = false)
    @field:NotNull(message = "Total amount cannot be null")
    @field:DecimalMin(value = "0.0", inclusive = true, message = "Total amount must be at least 0")
    var totalAmount: BigDecimal = BigDecimal.ZERO,

    @Column(name = "issued_at", nullable = false)
    @field:NotNull(message = "Issued at cannot be null")
    var issuedAt: LocalDateTime,

    @Column(name = "due_at")
    var dueAt: LocalDateTime? = null,

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