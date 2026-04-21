package com.finanzasana.modules.empresas.infrastructure.rest.dto

import kotlinx.serialization.Serializable

@Serializable
data class EmpresaPrestamoResponse(
    val id: Int,
    val nombre: String,
    val tasaInteres: Double
)
