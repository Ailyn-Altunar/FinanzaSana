package com.finanzasana.modules.empresas.infrastructure.rest.dto

import kotlinx.serialization.Serializable

@Serializable
data class EmpresaPrestamoRequest(
    val nombre: String,
    val tasaInteres: Double
)
