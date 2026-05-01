# CLAUDE.md — auth-service:data

> Load this file + root `CLAUDE.md` when working in `auth-service/data`. Do not load other service or submodule files.

## Role

Outbound adapters for the auth service. Implements `UserRepositoryPort` from `:domain/port/` using Spring Data JPA and PostgreSQL. Nothing in this module is visible to `:application` or `:api` except through domain port interfaces.

## Package Structure

```
rs.neozoic.reservation.auth.data
  model/
    entity/   ← JPA entity classes (internal to this module)
    mapper/   ← extension functions: toDomain() / toEntity()
  repository/
    UserRepository.kt            ← Spring Data JPA interface
    adapter/
      UserRepositoryAdapter.kt   ← implements UserRepositoryPort
```

## JPA Entities

- Annotate with `@Entity` and `@Table(name = "...")`.
- Keep entity classes strictly inside this module — never return them from adapter methods.
- Use an internal auto-generated `Long id` as the DB primary key and a `UUID publicId` as the externally visible identifier.
- Use `@Version` on timestamp fields where optimistic locking is needed.

```kotlin
@Entity
@Table(name = "users")
data class UserEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(unique = true, nullable = false) val publicId: UUID = UUID.randomUUID(),
    @Column(nullable = false) val email: String,
    ...
)
```

## Spring Data JPA Repositories

- Interface named `[Entity]Repository` extending `JpaRepository`.
- Query methods follow Spring Data naming conventions.

```kotlin
interface UserRepository : JpaRepository<UserEntity, Long> {
    fun findByEmail(email: String): UserEntity?
}
```

## Repository Adapters

- Class named `[Entity]RepositoryAdapter`, annotated `@Repository`.
- Implements the corresponding `[Entity]RepositoryPort` from `:domain/port/`.
- **Always map** entity → domain model before returning; domain model → entity before persisting.
- Never return a JPA entity outside this class.

```kotlin
@Repository
class UserRepositoryAdapter(
    private val userRepository: UserRepository
) : UserRepositoryPort {
    override fun createUser(user: User): User =
        userRepository.save(user.toEntity()).toDomain()

    override fun getUserByEmail(email: String): User? =
        userRepository.findByEmail(email)?.toDomain()
}
```

## Mappers

- Extension functions in `model/mapper/`, one file per entity.
- `EntityClass.toDomain()` and `DomainClass.toEntity()`.
- The entity's internal `Long id` is never exposed in the domain model; use `publicId` as `id`.

```kotlin
fun UserEntity.toDomain() = User(id = this.publicId, email = this.email, ...)
fun User.toEntity() = UserEntity(publicId = this.id ?: UUID.randomUUID(), email = this.email, ...)
```

## Database Migrations

- Liquibase changelogs in `src/main/resources/db/changelog/`.
- One changelog file per migration; reference from the master changelog.
