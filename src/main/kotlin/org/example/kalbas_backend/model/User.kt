package org.example.kalbas_backend.model

import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name = "users")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    
    @Column(nullable = false, unique = true)
    private val username: String,
    
    @Column(nullable = false)
    private var password: String,
    
    @Column(nullable = false, unique = true)
    val email: String,
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val role: Role = Role.ROLE_USER,
    
    @Column(nullable = false)
    private val isAccountNonExpired: Boolean = true,
    
    @Column(nullable = false)
    private val isAccountNonLocked: Boolean = true,
    
    @Column(nullable = false)
    private val isCredentialsNonExpired: Boolean = true,
    
    @Column(nullable = false)
    private val isEnabled: Boolean = true
) : UserDetails {

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return listOf(SimpleGrantedAuthority(role.name))
    }

    override fun getPassword(): String = password

    fun setPassword(password: String) {
        this.password = password
    }

    override fun getUsername(): String = username

    override fun isAccountNonExpired(): Boolean = isAccountNonExpired

    override fun isAccountNonLocked(): Boolean = isAccountNonLocked

    override fun isCredentialsNonExpired(): Boolean = isCredentialsNonExpired

    override fun isEnabled(): Boolean = isEnabled
}

