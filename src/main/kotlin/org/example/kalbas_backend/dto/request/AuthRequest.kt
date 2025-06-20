package org.example.kalbas_backend.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class LoginRequestDto(
    @field:NotBlank
    @field:Size(min = 3, max = 50)
    val identifier: String,

    @field:NotBlank
    @field:Size(min = 3, max = 40)
    val password: String
)

data class SignupRequestDto(
    @field:NotBlank
    @field:Size(min = 3, max = 20)
    val username: String,

    @field:NotBlank
    @field:Size(max = 50)
    val email: String,

    @field:NotBlank
    @field:Size(min = 3, max = 40)
    val password: String,

    @field:NotBlank
    @field:Size(min = 3, max = 40)
    val confirmPassword: String
)

data class TokenRefreshRequestDto(
    @field:NotBlank
    val refreshToken: String
) 