package com.finanzasana.modules.deudas.application.usecase

import com.finanzasana.modules.deudas.domain.model.Deuda
import com.finanzasana.modules.deudas.domain.repository.DeudaRepository

class VerDetalleDeudaUseCase(
    private val deudaRepository: DeudaRepository
) {

    suspend fun ejecutar(idDeuda: Int, idUsuario: Int): Deuda? {

        if (idDeuda <= 0) return null
        if (idUsuario <= 0) return null

        return deudaRepository.obtenerPorId(idDeuda, idUsuario)
    }
}
