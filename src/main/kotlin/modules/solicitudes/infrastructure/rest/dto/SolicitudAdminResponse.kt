package com.finanzasana.modules.solicitudes.infrastructure.rest.dto

import com.finanzasana.modules.solicitudes.domain.model.HistorialSolicitud
import com.finanzasana.modules.solicitudes.domain.model.PendienteSolicitud
import kotlinx.serialization.Serializable

@Serializable
data class SolicitudAdminResponse(
    val id: Int,
    val nombreUsuario: String,
    val nombreEmpresa: String? = null,
    val montoSolicitado: Double,
    val estado: String
)

fun HistorialSolicitud.toAdminResponse(): SolicitudAdminResponse =
    SolicitudAdminResponse(
        id = this.id,
        nombreUsuario = this.nombreUsuario,
        nombreEmpresa = this.nombreEmpresa,
        montoSolicitado = this.montoSolicitado,
        estado = when (this.estado) {
            2 -> "APROBADA"
            3 -> "RECHAZADA"
            else -> "DESCONOCIDO"
        }
    )

fun List<HistorialSolicitud>.toHistorialAdminResponse(): List<SolicitudAdminResponse> =
    this.map { it.toAdminResponse() }

fun PendienteSolicitud.toAdminResponse(): SolicitudAdminResponse =
    SolicitudAdminResponse(
        id = this.id,
        nombreUsuario = this.nombreUsuario,
        nombreEmpresa = this.nombreEmpresa,
        montoSolicitado = this.montoSolicitado,
        estado = "PENDIENTE"
    )

fun List<PendienteSolicitud>.toPendientesAdminResponse(): List<SolicitudAdminResponse> =
    this.map { it.toAdminResponse() }
