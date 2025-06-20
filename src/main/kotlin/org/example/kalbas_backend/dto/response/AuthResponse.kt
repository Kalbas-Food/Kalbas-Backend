package org.example.kalbas_backend.dto.response

data class LoginResponseDto(
    val token: String,
    val refreshToken: String,
    val id: Long,
    val username: String,
    val email: String,
    val roles: List<String>
)

data class TokenRefreshResponseDto(
    val accessToken: String,
    val refreshToken: String,
    val tokenType: String = "Bearer"
)

data class MessageResponseDto(
    val message: String
)