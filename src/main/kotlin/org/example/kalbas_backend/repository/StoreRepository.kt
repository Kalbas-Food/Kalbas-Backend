package org.example.kalbas_backend.repository

import org.example.kalbas_backend.model.Store
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StoreRepository : JpaRepository<Store, Long> {
    fun findByName(name: String): List<Store>
    fun findByOwnerId(ownerId: Long): List<Store>
} 