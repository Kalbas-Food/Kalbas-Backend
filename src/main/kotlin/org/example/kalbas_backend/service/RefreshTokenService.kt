package org.example.kalbas_backend.service

import org.example.kalbas_backend.exception.TokenRefreshException
import org.example.kalbas_backend.model.RefreshToken
import org.example.kalbas_backend.repository.RefreshTokenRepository
import org.example.kalbas_backend.repository.UserRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.util.*

@Service
class RefreshTokenService(
    private val refreshTokenRepository: RefreshTokenRepository,
    private val userRepository: UserRepository
) {
    @Value("\${jwt.refreshExpiration}")
    private var refreshTokenDurationMs: Long = 0

    fun findByToken(token: String): Optional<RefreshToken> {
        return refreshTokenRepository.findByToken(token)
    }

    fun createRefreshToken(userId: Long): RefreshToken {
        val user = userRepository.findById(userId)
            .orElseThrow { RuntimeException("User not found with id: $userId") }
        
        // Check if user already has a refresh token and delete it
        refreshTokenRepository.findByUser(user).ifPresent { 
            refreshTokenRepository.delete(it)
        }
        
        val refreshToken = RefreshToken(
            user = user,
            token = UUID.randomUUID().toString(),
            expiryDate = Instant.now().plusMillis(refreshTokenDurationMs)
        )

        return refreshTokenRepository.save(refreshToken)
    }

    fun verifyExpiration(token: RefreshToken): RefreshToken {
        if (token.expiryDate.isBefore(Instant.now())) {
            refreshTokenRepository.delete(token)
            throw TokenRefreshException(token.token, "Refresh token was expired. Please make a new signin request")
        }
        return token
    }

    @Transactional
    fun deleteByUserId(userId: Long): Int {
        val user = userRepository.findById(userId)
            .orElseThrow { RuntimeException("User not found with id: $userId") }
        return refreshTokenRepository.deleteByUser(user)
    }
} 