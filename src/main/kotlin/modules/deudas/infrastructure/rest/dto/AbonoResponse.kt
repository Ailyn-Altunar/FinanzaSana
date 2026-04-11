package com.finanzasana.modules.deudas.infrastructure.rest.dto

import com.finanzasana.modules.deudas.domain.model.Abono
import kotlinx.serialization.Serializable
import java.time.format.DateTimeFormatter

@Serializable
data class AbonoResponse(
    val id: Int,
    val monto: Double,
    val fecha: String
)

fun Abono.toResponse(): AbonoResponse {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    return AbonoResponse(
        id = this.id ?: 0,
        monto = this.monto,
        fecha = this.fecha.format(formatter)
    )
}
