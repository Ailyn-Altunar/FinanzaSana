package com.finanzasana.modules.solicitudes.domain.model

import java.time.LocalDateTime

data class SolicitudPrestamo(
    val id: Int? = null,
    val idUsuario: Int,
    val idEmpresa: Int,
    val montoSolicitado: Double,
    val meses: Int,
    val motivo: String,
    val tasaInteres: Double,
    val idCategoria: Int,
    val imagenBase64: String?,
    val latitud: Double?,
    val longitud: Double?,
    val estado: Int,
    val fechaSolicitud: LocalDateTime
) {
    init {
        require(idUsuario > 0) { "El usuario debe ser válido" }
        require(idEmpresa > 0) { "La empresa debe ser válida" }
        require(montoSolicitado > 0) { "El monto solicitado debe ser mayor a 0" }
        require(meses > 0) { "Los meses deben ser mayores a 0" }
        require(motivo.isNotBlank()) { "El motivo no puede estar vacío" }
        require(tasaInteres >= 0) { "La tasa de interés no puede ser negativa" }
        require(idCategoria > 0) { "La categoría debe ser válida" }
    }
}
