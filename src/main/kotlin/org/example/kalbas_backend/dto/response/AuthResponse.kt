package org.example.kalbas_backend.dto.response

data class JwtResponse(
    val token: String,
    val refreshToken: String,
    val id: Long,
    val username: String,
    val email: String,
    val roles: List<String>
)

data class TokenRefreshResponse(
    val accessToken: String,
    val refreshToken: String,
    val tokenType: String = "Bearer"
)

data class MessageResponse(
    val message: String
) 