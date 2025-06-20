package org.example.kalbas_backend.enums

enum class UserRoleEnum {
    ROLE_USER,
    ROLE_MODERATOR,
    ROLE_ADMIN;

    companion object {
        fun fromString(role: String): UserRoleEnum? {
            return UserRoleEnum.entries.find { it.name == role }
        }
    }
}