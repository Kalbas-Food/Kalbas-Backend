package org.example.kalbas_backend.repository

import org.example.kalbas_backend.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByUsername(username: String): Optional<User>
    fun findByEmail(email: String): Optional<User>
    
    @Query("SELECT u FROM User u WHERE u.username = :identifier OR u.email = :identifier")
    fun findByUsernameOrEmail(@Param("identifier") identifier: String): Optional<User>
    
    fun existsByUsername(username: String): Boolean
    fun existsByEmail(email: String): Boolean
}