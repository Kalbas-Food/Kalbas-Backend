package org.example.kalbas_backend.service

import org.example.kalbas_backend.dto.request.CategoryCreateRequestDto
import org.example.kalbas_backend.dto.request.CategoryUpdateRequestDto
import org.example.kalbas_backend.dto.response.CategoryResponseDto
import org.example.kalbas_backend.dto.response.CategoryParentDto
import org.example.kalbas_backend.model.Category
import org.example.kalbas_backend.repository.CategoryRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

interface CategoryService {
    fun createCategory(request: CategoryCreateRequestDto): CategoryResponseDto
    fun getCategoryById(id: Long): CategoryResponseDto
    fun getAllCategories(): List<CategoryResponseDto>
    fun getRootCategories(): List<CategoryResponseDto>
    fun getSubCategories(parentCategoryId: Long): List<CategoryResponseDto>
    fun updateCategory(id: Long, request: CategoryUpdateRequestDto): CategoryResponseDto
    fun deleteCategory(id: Long)
}

@Service
class CategoryServiceImpl(
    private val categoryRepository: CategoryRepository
) : CategoryService {

    @Transactional
    override fun createCategory(request: CategoryCreateRequestDto): CategoryResponseDto {
        val parentCategory: Category? = request.parentCategoryId?.let { parentId ->
            categoryRepository.findById(parentId)
                .orElseThrow { NoSuchElementException("Parent category not found with id: $parentId") }
        }
        val category = Category(
            name = request.name,
            imageUrl = request.imageUrl,
            parentCategory = parentCategory
        )
        val saved = categoryRepository.save(category)
        return saved.toDto()
    }

    @Transactional(readOnly = true)
    override fun getCategoryById(id: Long): CategoryResponseDto {
        val category = categoryRepository.findById(id)
            .orElseThrow { NoSuchElementException("Category not found with id: $id") }
        return category.toDto()
    }

    @Transactional(readOnly = true)
    override fun getAllCategories(): List<CategoryResponseDto> =
        categoryRepository.findAll().map { it.toDto() }

    @Transactional(readOnly = true)
    override fun getRootCategories(): List<CategoryResponseDto> =
        categoryRepository.findByParentCategoryIsNull().map { it.toDto() }

    @Transactional(readOnly = true)
    override fun getSubCategories(parentCategoryId: Long): List<CategoryResponseDto> =
        categoryRepository.findByParentCategoryId(parentCategoryId).map { it.toDto() }

    @Transactional
    override fun updateCategory(id: Long, request: CategoryUpdateRequestDto): CategoryResponseDto {
        val category = categoryRepository.findById(id)
            .orElseThrow { NoSuchElementException("Category not found with id: $id") }
        val parentCategory: Category? = request.parentCategoryId?.let { parentId ->
            categoryRepository.findById(parentId)
                .orElseThrow { NoSuchElementException("Parent category not found with id: $parentId") }
        }
        category.name = request.name
        category.imageUrl = request.imageUrl
        category.parentCategory = parentCategory
        category.isActive = request.isActive
        val updated = categoryRepository.save(category)
        return updated.toDto()
    }

    @Transactional
    override fun deleteCategory(id: Long) {
        if (!categoryRepository.existsById(id)) {
            throw NoSuchElementException("Category not found with id: $id")
        }
        categoryRepository.deleteById(id)
    }

    private fun Category.toDto(): CategoryResponseDto = CategoryResponseDto(
        id = this.id,
        name = this.name,
        imageUrl = this.imageUrl,
        parentCategory = this.parentCategory?.let { parent ->
            CategoryParentDto(
                id = parent.id,
                name = parent.name,
                imageUrl = parent.imageUrl
            )
        },
        isActive = this.isActive,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    )
} 