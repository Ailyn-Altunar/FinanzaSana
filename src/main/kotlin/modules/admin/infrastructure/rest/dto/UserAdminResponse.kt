package com.finanzasana.modules.admin.infrastructure.rest.dto

import com.finanzasana.modules.admin.domain.model.UserAdmin
import kotlinx.serialization.Serializable

@Serializable
data class UserAdminResponse(
    val id: Int,
    val nombre: String,
    val email: String,
    val totalDeudas: Int
)
fun UserAdmin.toResponse(): UserAdminResponse {
    return UserAdminResponse(
        id = id,
        nombre = nombre,
        email = email,
        totalDeudas = totalDeudas
    )
}
fun List<UserAdmin>.toResponse(): List<UserAdminResponse> =
    this.map { it.toResponse() }
