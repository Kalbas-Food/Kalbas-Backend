package org.example.kalbas_backend.repository

import org.example.kalbas_backend.model.RefreshToken
import org.example.kalbas_backend.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface RefreshTokenRepository : JpaRepository<RefreshToken, Long> {
    fun findByToken(token: String): Optional<RefreshToken>
    fun findByUser(user: User): Optional<RefreshToken>
    
    @Modifying
    fun deleteByUser(user: User): Int
} 