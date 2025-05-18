package rs.neozoic.reservation.auth.data.model.entity

import jakarta.persistence.*
import rs.neozoic.reservation.domain.model.Role
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "users")
data class UserEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null, // internal DB key

    @Column(name = "public_id", nullable = false, unique = true)
    val publicId: UUID = UUID.randomUUID(),

    @Column(name = "email", nullable = false, unique = true)
    val email: String,

    @Column(name = "password", nullable = false)
    val password: String,

    @Column(name = "enabled", nullable = false)
    val enabled: Boolean,

    @Column(name = "activated", nullable = false)
    val activated: Boolean,

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at", nullable = false)
    @Version
    val updatedAt: LocalDateTime = LocalDateTime.now(),

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
        name = "user_roles",
        joinColumns = [JoinColumn(name = "user_id")]
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    val roles: Set<Role> = setOf()
)