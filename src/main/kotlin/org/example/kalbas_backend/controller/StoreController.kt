package org.example.kalbas_backend.controller

import org.example.kalbas_backend.dto.request.StoreCreateRequestDto
import org.example.kalbas_backend.dto.request.StoreUpdateRequestDto
import org.example.kalbas_backend.dto.response.StoreResponseDto
import org.example.kalbas_backend.dto.response.MessageResponseDto
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
    fun createStore(@Validated @RequestBody request: StoreCreateRequestDto): ResponseEntity<StoreResponseDto> =
        ResponseEntity.ok(storeService.createStore(request))

    @GetMapping("/{id}")
    fun getStoreById(@PathVariable id: Long): ResponseEntity<StoreResponseDto> =
        ResponseEntity.ok(storeService.getStoreById(id))

    @GetMapping
    fun getAllStores(): ResponseEntity<List<StoreResponseDto>> =
        ResponseEntity.ok(storeService.getAllStores())

    @PutMapping("/{id}")
    fun updateStore(
        @PathVariable id: Long,
        @Validated @RequestBody request: StoreUpdateRequestDto
    ): ResponseEntity<StoreResponseDto> =
        ResponseEntity.ok(storeService.updateStore(id, request))

    @DeleteMapping("/{id}")
    fun deleteStore(@PathVariable id: Long): ResponseEntity<MessageResponseDto> {
        storeService.deleteStore(id)
        return ResponseEntity.ok(MessageResponseDto("Store deleted successfully"))
    }
} 