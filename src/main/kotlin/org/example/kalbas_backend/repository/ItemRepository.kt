package org.example.kalbas_backend.repository

import org.example.kalbas_backend.model.Item
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ItemRepository : JpaRepository<Item, Long> {
    fun findByStoreId(storeId: Long): List<Item>
}