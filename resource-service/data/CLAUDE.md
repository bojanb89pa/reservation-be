# CLAUDE.md — resource-service:data

> Load this file + root `CLAUDE.md` when working in `resource-service/data`. Do not load other service or submodule files.

## Role

Outbound adapters for the resource service. Implements `BusinessRepositoryPort` and `ReservationRepositoryPort` from `:domain/port/` using Spring Data JPA and PostgreSQL. Nothing in this module is visible to `:application` or `:api` except through domain port interfaces.

## Package Structure

```
rs.neozoic.reservation.resource.data
  model/
    entity/   ← JPA entity classes (internal to this module)
      BusinessEntity.kt
      BusinessAvailabilityEntity.kt
      ReservationEntity.kt
    mapper/   ← extension functions: toDomain() / toEntity()
      BusinessMappers.kt
      ReservationMappers.kt
  repository/
    BusinessRepository.kt            ← Spring Data JPA interface
    ReservationRepository.kt
    adapter/
      BusinessRepositoryAdapter.kt   ← implements BusinessRepositoryPort
      ReservationRepositoryAdapter.kt
```

## JPA Entities

- Annotate with `@Entity` and `@Table(name = "...")`.
- Keep entity classes strictly inside this module — never return them from adapter methods.
- Use an internal auto-generated `Long id` as the DB primary key and a `UUID publicId` as the externally visible identifier.
- Use `@Version` on timestamp fields where optimistic locking is needed.

```kotlin
@Entity
@Table(name = "businesses")
data class BusinessEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(name = "public_id", nullable = false, unique = true) val publicId: UUID = UUID.randomUUID(),
    @Column(name = "name", nullable = false) val name: String,
    @Column(name = "min_reservation_period", nullable = false) val minReservationDuration: Int = 30,
    @Column(name = "created_at", nullable = false) val createdAt: LocalDateTime = LocalDateTime.now(),
    @Column(name = "updated_at", nullable = false) @Version val updatedAt: LocalDateTime = LocalDateTime.now(),
)
```

## Spring Data JPA Repositories

- Interface named `[Entity]Repository` extending `JpaRepository`.
- Query methods follow Spring Data naming conventions.

```kotlin
interface BusinessRepository : JpaRepository<BusinessEntity, Long> {
    fun findByPublicId(publicId: UUID): BusinessEntity?
    fun existsByPublicId(publicId: UUID): Boolean
}
```

## Repository Adapters

- Class named `[Entity]RepositoryAdapter`, annotated `@Repository`.
- Implements the corresponding `[Entity]RepositoryPort` from `:domain/port/`.
- **Always map** entity → domain model before returning; domain model → entity before persisting.
- Never return a JPA entity outside this class.

```kotlin
@Repository
class BusinessRepositoryAdapter(
    private val businessRepository: BusinessRepository
) : BusinessRepositoryPort {
    override fun createBusiness(business: Business): Business =
        businessRepository.save(business.toEntity()).toDomain()

    override fun getBusinessByPublicId(publicId: UUID): Business? =
        businessRepository.findByPublicId(publicId)?.toDomain()

    override fun existByPublicId(publicId: UUID): Boolean =
        businessRepository.existsByPublicId(publicId)
}
```

## Mappers

- Extension functions in `model/mapper/`, one file per entity.
- `EntityClass.toDomain()` and `DomainClass.toEntity()`.
- The entity's internal `Long id` is never exposed in the domain model; use `publicId` as `id`.

```kotlin
fun BusinessEntity.toDomain() = Business(id = this.publicId, name = this.name)
fun Business.toEntity() = BusinessEntity(publicId = this.id ?: UUID.randomUUID(), name = this.name)
```

## Gradle Commands
Never run Gradle commands automatically. Print the command for the user:
> Run the command `{command}` and let me know if there is any issue.

## Unit Tests
`:data` adapters have no unit tests. Their correctness is validated by:
- The mapper functions (`toDomain` / `toEntity`) which are pure Kotlin and can be tested in isolation if needed.
- Integration tests against a real database (not yet in this project).

Do not mock `JpaRepository` — if a data-layer bug is suspected, verify by running the service and inspecting the DB directly.

## Database Migrations

- Liquibase changelogs in `src/main/resources/db/changelog/`.
- One changelog file per migration; reference from the master changelog.
