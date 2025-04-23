package org.example.kalbas_backend.controller

import org.example.kalbas_backend.service.UserServices
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.example.kalbas_backend.model.User

/**
 * Controller for managing user-related operations.
 */
@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val userServices: UserServices
) {
    @GetMapping
    fun getUsers(): List<User> {
        return userServices.getUsers()
    }
}