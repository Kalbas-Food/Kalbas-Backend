package org.example.kalbas_backend.controller

import org.example.kalbas_backend.dto.request.ItemCreateRequestDto
import org.example.kalbas_backend.dto.request.ItemUpdateRequestDto
import org.example.kalbas_backend.dto.response.ItemResponseDto
import org.example.kalbas_backend.dto.response.MessageResponseDto
import org.example.kalbas_backend.service.ItemService
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/items")
class ItemController(
    private val itemService: ItemService
) {
    @PostMapping
    fun createItem(@Validated @RequestBody request: ItemCreateRequestDto): ResponseEntity<ItemResponseDto> =
        ResponseEntity.ok(itemService.createItem(request))

    @GetMapping("/{id}")
    fun getItemById(@PathVariable id: Long): ResponseEntity<ItemResponseDto> =
        ResponseEntity.ok(itemService.getItemById(id))

    @GetMapping
    fun getAllItems(): ResponseEntity<List<ItemResponseDto>> =
        ResponseEntity.ok(itemService.getAllItems())

    @GetMapping("/store/{storeId}")
    fun getItemsByStore(@PathVariable storeId: Long): ResponseEntity<List<ItemResponseDto>> =
        ResponseEntity.ok(itemService.getItemsByStore(storeId))

    @PutMapping("/{id}")
    fun updateItem(
        @PathVariable id: Long,
        @Validated @RequestBody request: ItemUpdateRequestDto
    ): ResponseEntity<ItemResponseDto> =
        ResponseEntity.ok(itemService.updateItem(id, request))

    @DeleteMapping("/{id}")
    fun deleteItem(@PathVariable id: Long): ResponseEntity<MessageResponseDto> {
        itemService.deleteItem(id)
        return ResponseEntity.ok(MessageResponseDto("Item deleted successfully"))
    }
} 