package com.finanzasana.modules.deudas.infrastructure.rest.dto

import kotlinx.serialization.Serializable

@Serializable
data class TotalAdeudadoResponse(
    val cantidadDeudasActivas: Int,
    val totalAdeudadoActivo: Double
)
