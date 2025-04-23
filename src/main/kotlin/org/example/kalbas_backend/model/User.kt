package org.example.kalbas_backend.model

import jakarta.persistence.* // Correct import for JPA annotations
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant
import org.example.kalbas_backend.config.Constants

@Entity
@EntityListeners(AuditingEntityListener::class)
@Table(
    name = "users", schema = "public", uniqueConstraints = [
        UniqueConstraint(name = "uc_user_username", columnNames = ["username"])
    ]
)
class User() {

    @Id
    @GeneratedValue
    @Column(name = "id")
    var id: Long = 0

    @Column(name = "username", length = Constants.MAX_USERNAME_LENGTH)
    var username: String? = null

    @Column(name = "first_name", length = Constants.MAX_NAME_AND_LAST_NAME_LENGTH)
    lateinit var firstName: String

    @Column(name = "last_name", length = Constants.MAX_NAME_AND_LAST_NAME_LENGTH)
    var lastName: String? = null

    @Column(name = "email", length = Constants.MAX_EMAIL_LENGTH)
    var email: String? = null

    @CreatedDate
    @Column(name = "created_at")
    var createdAt: Instant = Instant.now()

    @LastModifiedDate
    @Column(name = "updated_at")
    var updatedAt: Instant? = null

    override fun toString(): String {
        return "User(id=$id, username=$username, firstName='$firstName', lastName=$lastName, email=$email, createdAt=$createdAt, updatedAt=$updatedAt)"
    }
}

