package com.finanzasana.modules.solicitudes.infrastructure.rest.dto

import kotlinx.serialization.Serializable

@Serializable
data class SolicitudPrestamoRequest(
    val idUsuario: Int,
    val idEmpresa: Int,
    val montoSolicitado: Double,
    val meses: Int,
    val motivo: String,
    val idCategoria: Int,
    val imagenBase64: String? = null,
    val latitud: Double? = null,
    val longitud: Double? = null
)
