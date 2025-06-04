package org.example.kalbas_backend.controller

import jakarta.validation.Valid
import org.example.kalbas_backend.dto.request.LoginRequest
import org.example.kalbas_backend.dto.request.SignupRequest
import org.example.kalbas_backend.dto.request.TokenRefreshRequest
import org.example.kalbas_backend.dto.response.JwtResponse
import org.example.kalbas_backend.dto.response.MessageResponse
import org.example.kalbas_backend.dto.response.TokenRefreshResponse
import org.example.kalbas_backend.exception.TokenRefreshException
import org.example.kalbas_backend.model.Role
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

    @PostMapping("/signin")
    fun authenticateUser(@Valid @RequestBody loginRequest: LoginRequest): ResponseEntity<JwtResponse> {
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(loginRequest.username, loginRequest.password)
        )

        SecurityContextHolder.getContext().authentication = authentication
        val jwt = jwtUtils.generateJwtToken(authentication)
        
        val userDetails = authentication.principal as User
        val roles = userDetails.authorities.map { it.authority }
        
        val refreshToken = refreshTokenService.createRefreshToken(userDetails.id!!)

        return ResponseEntity.ok(
            JwtResponse(
                token = jwt,
                refreshToken = refreshToken.token,
                id = userDetails.id,
                username = userDetails.username,
                email = userDetails.email,
                roles = roles
            )
        )
    }

    @PostMapping("/signup")
    fun registerUser(@Valid @RequestBody signupRequest: SignupRequest): ResponseEntity<MessageResponse> {
        if (userRepository.existsByUsername(signupRequest.username)) {
            return ResponseEntity
                .badRequest()
                .body(MessageResponse("Error: Username is already taken!"))
        }

        if (userRepository.existsByEmail(signupRequest.email)) {
            return ResponseEntity
                .badRequest()
                .body(MessageResponse("Error: Email is already in use!"))
        }

        // Create new user's account
        val user = User(
            username = signupRequest.username,
            email = signupRequest.email,
            password = passwordEncoder.encode(signupRequest.password),
            role = Role.ROLE_USER
        )

        userRepository.save(user)

        return ResponseEntity.ok(MessageResponse("User registered successfully!"))
    }

    @PostMapping("/refreshtoken")
    fun refreshToken(@Valid @RequestBody request: TokenRefreshRequest): ResponseEntity<TokenRefreshResponse> {
        val requestRefreshToken = request.refreshToken

        return refreshTokenService.findByToken(requestRefreshToken)
            .map { refreshTokenService.verifyExpiration(it) }
            .map { refreshToken ->
                val user = refreshToken.user
                val token = jwtUtils.generateTokenFromUsername(user.username)
                ResponseEntity.ok(TokenRefreshResponse(token, requestRefreshToken))
            }
            .orElseThrow { TokenRefreshException(requestRefreshToken, "Refresh token is not in database!") }
    }

    @PostMapping("/signout")
    fun logoutUser(): ResponseEntity<MessageResponse> {
        val principal = SecurityContextHolder.getContext().authentication.principal
        if (principal is User) {
            refreshTokenService.deleteByUserId(principal.id!!)
        }
        return ResponseEntity.ok(MessageResponse("Log out successful!"))
    }
} 