package org.example.kalbas_backend.controller

import org.example.kalbas_backend.dto.request.CategoryCreateRequestDto
import org.example.kalbas_backend.dto.request.CategoryUpdateRequestDto
import org.example.kalbas_backend.dto.response.CategoryResponseDto
import org.example.kalbas_backend.dto.response.MessageResponseDto
import org.example.kalbas_backend.service.CategoryService
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/categories")
class CategoryController(
    private val categoryService: CategoryService
) {
    @PostMapping
    fun createCategory(@Validated @RequestBody request: CategoryCreateRequestDto): ResponseEntity<CategoryResponseDto> =
        ResponseEntity.ok(categoryService.createCategory(request))

    @GetMapping("/{id}")
    fun getCategoryById(@PathVariable id: Long): ResponseEntity<CategoryResponseDto> =
        ResponseEntity.ok(categoryService.getCategoryById(id))

    @GetMapping
    fun getAllCategories(): ResponseEntity<List<CategoryResponseDto>> =
        ResponseEntity.ok(categoryService.getAllCategories())

    @GetMapping("/root")
    fun getRootCategories(): ResponseEntity<List<CategoryResponseDto>> =
        ResponseEntity.ok(categoryService.getRootCategories())

    @GetMapping("/{parentCategoryId}/subcategories")
    fun getSubCategories(@PathVariable parentCategoryId: Long): ResponseEntity<List<CategoryResponseDto>> =
        ResponseEntity.ok(categoryService.getSubCategories(parentCategoryId))

    @PutMapping("/{id}")
    fun updateCategory(
        @PathVariable id: Long,
        @Validated @RequestBody request: CategoryUpdateRequestDto
    ): ResponseEntity<CategoryResponseDto> =
        ResponseEntity.ok(categoryService.updateCategory(id, request))

    @DeleteMapping("/{id}")
    fun deleteCategory(@PathVariable id: Long): ResponseEntity<MessageResponseDto> {
        categoryService.deleteCategory(id)
        return ResponseEntity.ok(MessageResponseDto("Category deleted successfully"))
    }
} 