package com.finanzasana.modules.usuarios.infrastructure.rest.dto

import com.finanzasana.modules.usuarios.domain.model.Usuario
import kotlinx.serialization.Serializable

@Serializable
data class UsuarioRequest(
    val nombre: String,
    val email: String,
    val contrasena: String,
    val edad: Int,
    val idRol: Int // 1 = Admin, 2 = Cliente, 3 = SuperAdmin
) {
    fun toDomain() = Usuario(
        nombre = nombre,
        email = email,
        contrasena = contrasena,
        edad = edad,
        idRol = idRol
    )
}