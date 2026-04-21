package com.finanzasana.modules.admin.infrastructure.rest.dto

import com.finanzasana.modules.admin.domain.model.ActividadAdmin
import kotlinx.serialization.Serializable
import java.time.format.DateTimeFormatter

@Serializable
data class ActividadAdminResponse(
    val nombreUsuario: String,
    val montoAbono: Double,
    val idDeuda: Int,
    val fecha: String
)

fun ActividadAdmin.toResponse(): ActividadAdminResponse {
    val formatter = DateTimeFormatter.ISO_DATE_TIME

    return ActividadAdminResponse(
        nombreUsuario = nombreUsuario,
        montoAbono = montoAbono,
        idDeuda = idDeuda,
        fecha = fecha.format(formatter)
    )
}

fun List<ActividadAdmin>.toResponse(): List<ActividadAdminResponse> =
    this.map { it.toResponse() }
