package org.example.kalbas_backend.controller

import org.example.kalbas_backend.dto.request.CategoryCreateRequestDto
import org.example.kalbas_backend.dto.request.CategoryUpdateRequestDto
import org.example.kalbas_backend.dto.response.CategoryResponseDto
import org.example.kalbas_backend.dto.response.MessageResponseDto
import org.example.kalbas_backend.dto.response.ApiResponse
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
    fun createCategory(@Validated @RequestBody request: CategoryCreateRequestDto): ResponseEntity<ApiResponse<CategoryResponseDto>> =
        ResponseEntity.ok(
            ApiResponse(
                success = true,
                message = "Category created successfully",
                data = categoryService.createCategory(request)
            )
        )

    @GetMapping("/{id}")
    fun getCategoryById(@PathVariable id: Long): ResponseEntity<ApiResponse<CategoryResponseDto>> =
        ResponseEntity.ok(
            ApiResponse(
                success = true,
                message = "Category retrieved successfully",
                data = categoryService.getCategoryById(id)
            )
        )

    @GetMapping
    fun getAllCategories(): ResponseEntity<ApiResponse<List<CategoryResponseDto>>> =
        ResponseEntity.ok(
            ApiResponse(
                success = true,
                message = "Categories retrieved successfully",
                data = categoryService.getAllCategories()
            )
        )

    @GetMapping("/root")
    fun getRootCategories(): ResponseEntity<ApiResponse<List<CategoryResponseDto>>> =
        ResponseEntity.ok(
            ApiResponse(
                success = true,
                message = "Root categories retrieved successfully",
                data = categoryService.getRootCategories()
            )
        )

    @GetMapping("/{parentCategoryId}/subcategories")
    fun getSubCategories(@PathVariable parentCategoryId: Long): ResponseEntity<ApiResponse<List<CategoryResponseDto>>> =
        ResponseEntity.ok(
            ApiResponse(
                success = true,
                message = "Subcategories retrieved successfully",
                data = categoryService.getSubCategories(parentCategoryId)
            )
        )

    @PutMapping("/{id}")
    fun updateCategory(
        @PathVariable id: Long,
        @Validated @RequestBody request: CategoryUpdateRequestDto
    ): ResponseEntity<ApiResponse<CategoryResponseDto>> =
        ResponseEntity.ok(
            ApiResponse(
                success = true,
                message = "Category updated successfully",
                data = categoryService.updateCategory(id, request)
            )
        )

    @DeleteMapping("/{id}")
    fun deleteCategory(@PathVariable id: Long): ResponseEntity<ApiResponse<MessageResponseDto>> {
        categoryService.deleteCategory(id)
        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                message = "Category deleted successfully",
                data = MessageResponseDto("Category deleted successfully")
            )
        )
    }
} 