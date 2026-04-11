package com.finanzasana.modules.usuarios.infrastructure.rest.dto

import com.finanzasana.modules.usuarios.domain.model.Usuario
import kotlinx.serialization.Serializable

@Serializable
data class UsuarioResponse(
    val id: Int,
    val nombre: String,
    val email: String,
    val rol: String,
    val telefono: String
)

fun Usuario.toResponse(): UsuarioResponse =
    UsuarioResponse(
        id = this.id ?: 0,
        nombre = this.nombre,
        email = this.email,
        rol = this.nombreRol ?: "DESCONOCIDO",
        telefono = this.telefono
    )
