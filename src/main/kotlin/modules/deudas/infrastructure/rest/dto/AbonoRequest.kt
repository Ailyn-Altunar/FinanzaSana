package com.finanzasana.modules.deudas.infrastructure.rest.dto

import com.finanzasana.modules.deudas.domain.model.Abono
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class AbonoRequest(
    val monto: Double
) {
    fun toDomain(idDeuda: Int, idUsuario: Int) = Abono(
        idDeuda = idDeuda,
        idUsuario = idUsuario,
        monto = monto,
        fecha = LocalDateTime.now()
    )
}
