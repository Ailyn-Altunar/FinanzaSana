package com.finanzasana.modules.admin.infrastructure.rest.dto

import com.finanzasana.modules.admin.domain.model.AdminMetrics
import kotlinx.serialization.Serializable

@Serializable
data class AdminMetricsResponse(
    val usuariosTotales: Int,
    val montoGlobal: Double,
    val deudasVencidas: Int
)

fun AdminMetrics.toResponse(): AdminMetricsResponse {
    return AdminMetricsResponse(
        usuariosTotales = usuariosTotales,
        montoGlobal = montoGlobal,
        deudasVencidas = deudasVencidas
    )
}
