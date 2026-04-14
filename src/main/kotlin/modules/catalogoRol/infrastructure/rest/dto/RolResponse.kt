package com.finanzasana.modules.catalogoRol.infrastructure.rest.dto

import kotlinx.serialization.Serializable

@Serializable
data class RolResponse(
    val idRol: Int,
    val nombre: String
)
