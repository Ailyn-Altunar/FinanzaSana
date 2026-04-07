package com.finanzasana.modules.usuarios.infrastructure.rest.dto

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val token: String,
    val usuario: UsuarioResponse
)