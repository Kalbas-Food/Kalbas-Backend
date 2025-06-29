package org.example.kalbas_backend.repository

import org.example.kalbas_backend.model.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : JpaRepository<Product, Long> {
    fun findByStoreId(storeId: Long): List<Product>
} 