package org.example.kalbas_backend.service

import org.example.kalbas_backend.dto.request.ItemCreateRequestDto
import org.example.kalbas_backend.dto.request.ItemUpdateRequestDto
import org.example.kalbas_backend.dto.response.ItemResponseDto
import org.example.kalbas_backend.dto.response.ItemStoreDto
import org.example.kalbas_backend.model.Item
import org.example.kalbas_backend.model.Store
import org.example.kalbas_backend.repository.ItemRepository
import org.example.kalbas_backend.repository.StoreRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

interface ItemService {
    fun createItem(request: ItemCreateRequestDto): ItemResponseDto
    fun getItemById(id: Long): ItemResponseDto
    fun getAllItems(): List<ItemResponseDto>
    fun getItemsByStore(storeId: Long): List<ItemResponseDto>
    fun updateItem(id: Long, request: ItemUpdateRequestDto): ItemResponseDto
    fun deleteItem(id: Long)
}

@Service
class ItemServiceImpl(
    private val itemRepository: ItemRepository,
    private val storeRepository: StoreRepository
) : ItemService {

    @Transactional
    override fun createItem(request: ItemCreateRequestDto): ItemResponseDto {
        val store: Store = storeRepository.findById(request.storeId)
            .orElseThrow { NoSuchElementException("Store not found with id: ${request.storeId}") }
        val item = Item(
            store = store,
            name = request.name,
            imageUrl = request.imageUrl,
            description = request.description,
            cost = request.cost
        )
        val saved = itemRepository.save(item)
        return saved.toDto()
    }

    @Transactional(readOnly = true)
    override fun getItemById(id: Long): ItemResponseDto {
        val item = itemRepository.findById(id)
            .orElseThrow { NoSuchElementException("Item not found with id: $id") }
        return item.toDto()
    }

    @Transactional(readOnly = true)
    override fun getAllItems(): List<ItemResponseDto> =
        itemRepository.findAll().map { it.toDto() }

    @Transactional(readOnly = true)
    override fun getItemsByStore(storeId: Long): List<ItemResponseDto> =
        itemRepository.findByStoreId(storeId).map { it.toDto() }

    @Transactional
    override fun updateItem(id: Long, request: ItemUpdateRequestDto): ItemResponseDto {
        val item = itemRepository.findById(id)
            .orElseThrow { NoSuchElementException("Item not found with id: $id") }
        item.name = request.name
        item.imageUrl = request.imageUrl
        item.description = request.description
        item.cost = request.cost
        item.isActive = request.isActive
        val updated = itemRepository.save(item)
        return updated.toDto()
    }

    @Transactional
    override fun deleteItem(id: Long) {
        if (!itemRepository.existsById(id)) {
            throw NoSuchElementException("Item not found with id: $id")
        }
        itemRepository.deleteById(id)
    }

    private fun Item.toDto(): ItemResponseDto = ItemResponseDto(
        id = this.id,
        store = ItemStoreDto(
            id = this.store.id,
            name = this.store.name
        ),
        name = this.name,
        imageUrl = this.imageUrl,
        description = this.description,
        cost = this.cost,
        isActive = this.isActive,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    )
}