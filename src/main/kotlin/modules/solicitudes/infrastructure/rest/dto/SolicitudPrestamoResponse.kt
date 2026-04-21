package com.finanzasana.modules.solicitudes.infrastructure.rest.dto

import com.finanzasana.modules.solicitudes.domain.model.SolicitudPrestamo
import kotlinx.serialization.Serializable

@Serializable
data class SolicitudPrestamoResponse(
    val id: Int,
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
    val fechaSolicitud: String
)

fun SolicitudPrestamo.toResponse(): SolicitudPrestamoResponse =
    SolicitudPrestamoResponse(
        id = this.id ?: 0,
        idUsuario = this.idUsuario,
        idEmpresa = this.idEmpresa,
        montoSolicitado = this.montoSolicitado,
        meses = this.meses,
        motivo = this.motivo,
        tasaInteres = this.tasaInteres,
        idCategoria = this.idCategoria,
        imagenBase64 = this.imagenBase64,
        latitud = this.latitud,
        longitud = this.longitud,
        estado = this.estado,
        fechaSolicitud = this.fechaSolicitud.toString()
    )
