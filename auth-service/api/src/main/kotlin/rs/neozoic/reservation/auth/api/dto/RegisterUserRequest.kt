package rs.neozoic.reservation.auth.api.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import rs.neozoic.reservation.domain.model.UserRegistration

data class RegisterUserRequest(
    @field:NotBlank @field:Email val email: String,
    @field:NotBlank val password: String,
    @field:NotBlank val firstName: String,
    @field:NotBlank val lastName: String
) {
    fun toDomain() = UserRegistration(
        email = email,
        password = password,
        firstName = firstName,
        lastName = lastName
    )
}
