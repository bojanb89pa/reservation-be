# Use Cases

## Auth Service

### Register User — `CreateUserUseCase`

**Endpoint:** `POST /api/users`
**Authentication:** None

Steps:
1. Check that no user with the given email exists via `UserRepositoryPort.existsByEmail`.
2. Hash the password using `PasswordEncoder`.
3. Persist the user via `UserRepositoryPort.save`.
4. Return the persisted user.

**Guard:** Throws `RuntimeException` if email is already registered *(TODO: replace with a typed domain exception)*.

---

### Look Up User by Email — `GetUserByEmailUseCase`

**Endpoint:** `GET /api/users?email=`
**Authentication:** None

Steps:
1. Query `UserRepositoryPort.findByEmail(email)`.
2. Return the user if found, `null` otherwise (controller returns 404).

**Also used internally** by `UserDetailsServiceImpl` to load users during Spring Security authentication.

---

## Resource Service

### Create Business — `CreateBusinessUseCase`

**Endpoint:** `POST /api/businesses`
**Authentication:** Bearer token required

Steps:
1. Delegate to `BusinessRepositoryPort.createBusiness(business)`.
2. Return the persisted `Business` with its assigned public UUID.

---

### Get Business — `GetBusinessUseCase`

**Endpoint:** `GET /api/businesses/{id}`
**Authentication:** Bearer token required

Steps:
1. Query `BusinessRepositoryPort.getBusinessByPublicId(id)`.
2. Return the business if found, `null` otherwise.

---

### Book Reservation — `CreateReservationUseCase`

**Endpoint:** `POST /api/businesses/{businessId}/reservations`
**Authentication:** Bearer token required — `userId` is extracted from the JWT principal

Steps:
1. Verify the target business exists via `BusinessRepositoryPort.existByPublicId(businessId)`.
2. If not found, throw `IllegalArgumentException("Business not found")` *(TODO: typed exception)*.
3. Persist via `ReservationRepositoryPort.createReservation(userId, businessId, reservation)`.
4. Return the persisted `Reservation` with its assigned ID.

---

### Get Reservation — `GetReservationUseCase`

**Endpoint:** `GET /api/businesses/{businessId}/reservations/{id}`
**Authentication:** Bearer token required

Steps:
1. Delegate to `ReservationRepositoryPort.getReservation(id)`.
2. Return the reservation (repository throws if not found).

---

## JWT Authentication Flow

1. Client sends credentials to `auth-service /oauth2/token`.
2. Spring Authorization Server authenticates the user; `JwtTokenCustomizer` embeds `user_id`, `email`, and `roles` as custom JWT claims.
3. Client includes `Authorization: Bearer <token>` in requests to `resource-service`.
4. `ResourceServerSecurityConfig` validates the JWT signature using the JWK endpoint (`http://localhost:8081/oauth2/jwks`).
5. `JwtToUserConverter` extracts an `AuthenticatedUser` from JWT claims — no runtime call to `auth-service`.
6. `AuthenticatedUser` is injected into controllers via `@AuthenticationPrincipal`.
