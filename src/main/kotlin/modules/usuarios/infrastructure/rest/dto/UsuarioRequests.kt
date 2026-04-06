package com.finanzasana.modules.usuarios.infrastructure.rest.dto

import com.finanzasana.modules.usuarios.domain.model.Usuario
import kotlinx.serialization.Serializable

@Serializable
data class UsuarioRequest(
    val nombre: String,
    val email: String,
    val contrasena: String,
    val idRol: Int
) {
    fun toDomain() = Usuario(
        nombre = nombre,
        email = email,
        contrasena = contrasena,
        idRol = idRol
    )
}