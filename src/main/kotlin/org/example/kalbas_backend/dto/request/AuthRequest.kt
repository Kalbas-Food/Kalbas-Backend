package org.example.kalbas_backend.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class LoginRequest(
    @field:NotBlank
    val username: String,
    
    @field:NotBlank
    val password: String
)

data class SignupRequest(
    @field:NotBlank
    @field:Size(min = 3, max = 20)
    val username: String,
    
    @field:NotBlank
    @field:Size(min = 6, max = 40)
    val password: String,
    
    @field:NotBlank
    @field:Size(max = 50)
    val email: String
)

data class TokenRefreshRequest(
    @field:NotBlank
    val refreshToken: String
) 