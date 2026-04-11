package com.finanzasana.modules.deudas.application.usecase

import com.finanzasana.modules.deudas.domain.model.Deuda
import com.finanzasana.modules.deudas.domain.repository.DeudaRepository

class RegistrarDeudaUseCase(
    private val deudaRepository: DeudaRepository
) {

    suspend fun ejecutar(deuda: Deuda): Deuda {
        // El dominio ya valida:
        // - concepto no vacío
        // - montoOriginal > 0
        // - saldoActual >= 0
        // - tasaInteres válida
        // - idCategoria > 0
        // - idUsuario > 0
        //
        // No se duplican validaciones aquí.

        return deudaRepository.registrar(deuda)
    }
}
