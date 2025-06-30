package org.example.kalbas_backend.controller

import org.example.kalbas_backend.dto.request.StoreCreateRequestDto
import org.example.kalbas_backend.dto.request.StoreUpdateRequestDto
import org.example.kalbas_backend.dto.response.StoreResponseDto
import org.example.kalbas_backend.dto.response.MessageResponseDto
import org.example.kalbas_backend.dto.response.ApiResponse
import org.example.kalbas_backend.service.StoreService
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/stores")
class StoreController(
    private val storeService: StoreService
) {
    @PostMapping
    fun createStore(@Validated @RequestBody request: StoreCreateRequestDto): ResponseEntity<ApiResponse<StoreResponseDto>> =
        ResponseEntity.ok(
            ApiResponse(
                success = true,
                message = "Store created successfully",
                data = storeService.createStore(request)
            )
        )

    @GetMapping("/{id}")
    fun getStoreById(@PathVariable id: Long): ResponseEntity<ApiResponse<StoreResponseDto>> =
        ResponseEntity.ok(
            ApiResponse(
                success = true,
                message = "Store retrieved successfully",
                data = storeService.getStoreById(id)
            )
        )

    @GetMapping
    fun getAllStores(): ResponseEntity<ApiResponse<List<StoreResponseDto>>> =
        ResponseEntity.ok(
            ApiResponse(
                success = true,
                message = "Stores retrieved successfully",
                data = storeService.getAllStores()
            )
        )

    @PutMapping("/{id}")
    fun updateStore(
        @PathVariable id: Long,
        @Validated @RequestBody request: StoreUpdateRequestDto
    ): ResponseEntity<ApiResponse<StoreResponseDto>> =
        ResponseEntity.ok(
            ApiResponse(
                success = true,
                message = "Store updated successfully",
                data = storeService.updateStore(id, request)
            )
        )

    @DeleteMapping("/{id}")
    fun deleteStore(@PathVariable id: Long): ResponseEntity<ApiResponse<MessageResponseDto>> {
        storeService.deleteStore(id)
        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                message = "Store deleted successfully",
                data = MessageResponseDto("Store deleted successfully")
            )
        )
    }
} 