package org.example.kalbas_backend.controller

import org.example.kalbas_backend.dto.response.ApiResponse
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.http.ResponseEntity

@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/api/test")
class TestController {
    
    @GetMapping("/all")
    fun allAccess(): ResponseEntity<ApiResponse<String>> =
        ResponseEntity.ok(ApiResponse(success = true, message = "Public content retrieved successfully", data = "Public Content."))
    
    @GetMapping("/user")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    fun userAccess(): ResponseEntity<ApiResponse<String>> =
        ResponseEntity.ok(ApiResponse(success = true, message = "User content retrieved successfully", data = "User Content."))
    
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    fun adminAccess(): ResponseEntity<ApiResponse<String>> =
        ResponseEntity.ok(ApiResponse(success = true, message = "Admin board retrieved successfully", data = "Admin Board."))
} 