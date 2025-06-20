package org.example.kalbas_backend.model

import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "refresh_tokens")
class RefreshToken(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    val user: User? = null,
    
    @Column(nullable = false, unique = true)
    var token: String = "",
    
    @Column(nullable = false)
    var expiryDate: Instant = Instant.now()
) {
    // No-argument constructor required by JPA
    constructor() : this(
        id = null,
        user = null,
        token = "",
        expiryDate = Instant.now()
    )
} 