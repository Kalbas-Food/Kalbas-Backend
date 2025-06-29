package org.example.kalbas_backend.repository

import org.example.kalbas_backend.model.Category
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepository : JpaRepository<Category, Long> {
    fun findByParentCategoryId(parentCategoryId: Long?): List<Category>
    fun findByParentCategoryIsNull(): List<Category>
} 