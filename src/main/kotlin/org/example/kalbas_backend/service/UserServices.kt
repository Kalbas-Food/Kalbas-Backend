package org.example.kalbas_backend.service

import org.example.kalbas_backend.model.User
import org.example.kalbas_backend.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserServices (
    private val userRepository: UserRepository
) {
    fun getUsers() : List<User> {
        return userRepository.findAll()
    }

    fun getUserById(id: Long): User? {
        return userRepository.findById(id).orElse(null)
    }

    fun createUser(user: User): User {
        return userRepository.save(user)
    }
}