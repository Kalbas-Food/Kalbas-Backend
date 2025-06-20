package org.example.kalbas_backend.service

import org.example.kalbas_backend.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserDetailsServiceImpl(private val userRepository: UserRepository) : UserDetailsService {

    @Transactional
    override fun loadUserByUsername(usernameOrEmail: String): UserDetails =
        userRepository.findByUsernameOrEmail(usernameOrEmail)
            .orElseThrow { UsernameNotFoundException("User not found with username or email: $usernameOrEmail") }
} 