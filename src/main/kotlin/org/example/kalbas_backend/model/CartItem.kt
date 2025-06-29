package org.example.kalbas_backend.model

import jakarta.persistence.*
import jakarta.validation.constraints.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "cart_items")
class CartItem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cart_id", nullable = false)
    @field:NotNull(message = "Cart cannot be null")
    var cart: Cart,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    @field:NotNull(message = "Product cannot be null")
    var product: Product,

    @Column(name = "quantity", nullable = false)
    @field:Min(value = 1, message = "Quantity must be at least 1")
    var quantity: Int,

    @Column(name = "price_at_add", nullable = false)
    @field:NotNull(message = "Price at add cannot be null")
    @field:DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    var priceAtAdd: BigDecimal,

    @Column(name = "is_active", nullable = false)
    var isActive: Boolean = true,

    @CreationTimestamp
    @Column(updatable = false)
    var createdAt: LocalDateTime? = null,

    @UpdateTimestamp
    var updatedAt: LocalDateTime? = null
) 