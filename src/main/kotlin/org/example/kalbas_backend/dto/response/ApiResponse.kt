package org.example.kalbas_backend.dto.response

import java.time.LocalDateTime

data class ApiResponse<T>(
    val success: Boolean,
    val message: String? = null,
    val data: T? = null,
    val metadata: Any? = null,
    val timestamp: LocalDateTime = LocalDateTime.now()
) 