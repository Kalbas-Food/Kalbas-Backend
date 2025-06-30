package org.example.kalbas_backend.controller

import jakarta.validation.Valid
import org.example.kalbas_backend.dto.request.LoginRequestDto
import org.example.kalbas_backend.dto.request.SignupRequestDto
import org.example.kalbas_backend.dto.request.TokenRefreshRequestDto
import org.example.kalbas_backend.dto.response.LoginResponseDto
import org.example.kalbas_backend.dto.response.MessageResponseDto
import org.example.kalbas_backend.dto.response.TokenRefreshResponseDto
import org.example.kalbas_backend.dto.response.ApiResponse
import org.example.kalbas_backend.enums.UserRoleEnum
import org.example.kalbas_backend.exception.TokenRefreshException
import org.example.kalbas_backend.model.User
import org.example.kalbas_backend.repository.UserRepository
import org.example.kalbas_backend.security.JwtUtils
import org.example.kalbas_backend.service.RefreshTokenService
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = ["*"], maxAge = 3600)
class AuthController(
    private val authenticationManager: AuthenticationManager,
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtUtils: JwtUtils,
    private val refreshTokenService: RefreshTokenService
) {

    @PostMapping("/login")
    fun authenticateUser(@Valid @RequestBody loginRequest: LoginRequestDto): ResponseEntity<ApiResponse<LoginResponseDto>> {
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(loginRequest.identifier, loginRequest.password)
        )

        SecurityContextHolder.getContext().authentication = authentication
        val jwt = jwtUtils.generateJwtToken(authentication)

        val userDetails = authentication.principal as User
        val roles = userDetails.authorities.map { it.authority }

        val refreshToken = refreshTokenService.createRefreshToken(userDetails.id!!)

        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                message = "Login successful",
                data = LoginResponseDto(
                    token = jwt,
                    refreshToken = refreshToken.token,
                    id = userDetails.id,
                    username = userDetails.username,
                    email = userDetails.email,
                    roles = roles
                )
            )
        )
    }

    @PostMapping("/signup")
    fun registerUser(@Valid @RequestBody signupRequest: SignupRequestDto): ResponseEntity<ApiResponse<MessageResponseDto>> {
        if (userRepository.existsByUsername(signupRequest.username)) {
            return ResponseEntity
                .badRequest()
                .body(ApiResponse(
                    success = false,
                    message = "Error: Username is already taken!",
                    data = MessageResponseDto("Error: Username is already taken!")
                ))
        }

        if (userRepository.existsByEmail(signupRequest.email)) {
            return ResponseEntity
                .badRequest()
                .body(ApiResponse(
                    success = false,
                    message = "Error: Email is already in use!",
                    data = MessageResponseDto("Error: Email is already in use!")
                ))
        }

        if (signupRequest.password != signupRequest.confirmPassword) {
            return ResponseEntity
                .badRequest()
                .body(ApiResponse(
                    success = false,
                    message = "Error: Passwords do not match!",
                    data = MessageResponseDto("Error: Passwords do not match!")
                ))
        }

        // Create a new user's account
        val user = User(
            username = signupRequest.username,
            email = signupRequest.email,
            password = passwordEncoder.encode(signupRequest.password),
            role = UserRoleEnum.ROLE_USER
        )

        userRepository.save(user)

        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                message = "User registered successfully!",
                data = MessageResponseDto("User registered successfully!")
            )
        )
    }

    @PostMapping("/refreshtoken")
    fun refreshToken(@Valid @RequestBody request: TokenRefreshRequestDto): ResponseEntity<ApiResponse<TokenRefreshResponseDto>> {
        val requestRefreshToken = request.refreshToken

        return refreshTokenService.findByToken(requestRefreshToken)
            .map { refreshTokenService.verifyExpiration(it) }
            .map { refreshToken ->
                val user = refreshToken.user ?: throw TokenRefreshException(requestRefreshToken, "User not found for refresh token!")
                val token = jwtUtils.generateTokenFromUsername(user.username)
                ResponseEntity.ok(
                    ApiResponse(
                        success = true,
                        message = "Token refreshed successfully",
                        data = TokenRefreshResponseDto(token, requestRefreshToken)
                    )
                )
            }
            .orElseThrow { TokenRefreshException(requestRefreshToken, "Refresh token is not in database!") }
    }

    @PostMapping("/signout")
    fun logoutUser(): ResponseEntity<ApiResponse<MessageResponseDto>> {
        val principal = SecurityContextHolder.getContext().authentication.principal
        if (principal is User) {
            refreshTokenService.deleteByUserId(principal.id!!)
        }
        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                message = "Log out successful!",
                data = MessageResponseDto("Log out successful!")
            )
        )
    }
} 