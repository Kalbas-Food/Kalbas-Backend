package org.example.kalbas_backend.service

import org.example.kalbas_backend.dto.request.StoreCreateRequestDto
import org.example.kalbas_backend.dto.request.StoreUpdateRequestDto
import org.example.kalbas_backend.dto.response.StoreResponseDto
import org.example.kalbas_backend.dto.response.StoreOwnerDto
import org.example.kalbas_backend.model.Store
import org.example.kalbas_backend.model.User
import org.example.kalbas_backend.repository.StoreRepository
import org.example.kalbas_backend.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

interface StoreService {
    fun createStore(request: StoreCreateRequestDto): StoreResponseDto
    fun getStoreById(id: Long): StoreResponseDto
    fun getAllStores(): List<StoreResponseDto>
    fun updateStore(id: Long, request: StoreUpdateRequestDto): StoreResponseDto
    fun deleteStore(id: Long)
}

@Service
class StoreServiceImpl(
    private val storeRepository: StoreRepository,
    private val userRepository: UserRepository
) : StoreService {

    @Transactional
    override fun createStore(request: StoreCreateRequestDto): StoreResponseDto {
        val owner: User = userRepository.findById(request.ownerId)
            .orElseThrow { NoSuchElementException("User not found with id: ${request.ownerId}") }
        val store = Store(
            owner = owner,
            name = request.name,
            description = request.description,
            address = request.address,
            phoneNumber = request.phoneNumber,
            openingHours = request.openingHours,
            closingHours = request.closingHours,
            iconImageUrl = request.iconImageUrl,
            coverImageUrl = request.coverImageUrl
        )
        val saved = storeRepository.save(store)
        return saved.toDto()
    }

    @Transactional(readOnly = true)
    override fun getStoreById(id: Long): StoreResponseDto {
        val store = storeRepository.findById(id)
            .orElseThrow { NoSuchElementException("Store not found with id: $id") }
        return store.toDto()
    }

    @Transactional(readOnly = true)
    override fun getAllStores(): List<StoreResponseDto> =
        storeRepository.findAll().map { it.toDto() }

    @Transactional
    override fun updateStore(id: Long, request: StoreUpdateRequestDto): StoreResponseDto {
        val store = storeRepository.findById(id)
            .orElseThrow { NoSuchElementException("Store not found with id: $id") }
        store.name = request.name
        store.description = request.description
        store.address = request.address
        store.phoneNumber = request.phoneNumber
        store.openingHours = request.openingHours
        store.closingHours = request.closingHours
        store.iconImageUrl = request.iconImageUrl
        store.coverImageUrl = request.coverImageUrl
        store.isActive = request.isActive
        store.isOpen = request.isOpen
        val updated = storeRepository.save(store)
        return updated.toDto()
    }

    @Transactional
    override fun deleteStore(id: Long) {
        if (!storeRepository.existsById(id)) {
            throw NoSuchElementException("Store not found with id: $id")
        }
        storeRepository.deleteById(id)
    }

    private fun Store.toDto(): StoreResponseDto = StoreResponseDto(
        id = this.id,
        owner = StoreOwnerDto(
            id = this.owner.id,
            username = this.owner.username,
            email = this.owner.email
        ),
        name = this.name,
        description = this.description,
        address = this.address,
        phoneNumber = this.phoneNumber,
        isActive = this.isActive,
        isOpen = this.isOpen,
        openingHours = this.openingHours,
        closingHours = this.closingHours,
        iconImageUrl = this.iconImageUrl,
        coverImageUrl = this.coverImageUrl,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    )
} 