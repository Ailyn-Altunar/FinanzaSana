package com.finanzasana.modules.deudas.application.usecase

import com.finanzasana.modules.deudas.domain.repository.DeudaRepository
import com.finanzasana.modules.deudas.infrastructure.rest.dto.TotalAdeudadoResponse
import java.time.LocalDate

class ObtenerTotalAdeudadoUseCase(
    private val deudaRepository: DeudaRepository
) {

    suspend fun ejecutar(idUsuario: Int): TotalAdeudadoResponse {
        if (idUsuario <= 0) {
            return TotalAdeudadoResponse(
                cantidadDeudasActivas = 0,
                totalAdeudadoActivo = 0.0
            )
        }

        val hoy = LocalDate.now()

        val deudasActivas = deudaRepository
            .listarPorUsuario(idUsuario)
            .filter { deuda ->
                deuda.saldoActual > 0 &&
                    (deuda.fechaVencimiento.isEqual(hoy) || deuda.fechaVencimiento.isAfter(hoy))
            }

        return TotalAdeudadoResponse(
            cantidadDeudasActivas = deudasActivas.size,
            totalAdeudadoActivo = deudasActivas.sumOf { it.saldoActual }
        )
    }
}
