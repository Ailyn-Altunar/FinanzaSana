package com.finanzasana.modules.deudas.application.usecase

import com.finanzasana.modules.deudas.domain.model.Deuda
import com.finanzasana.modules.deudas.domain.repository.DeudaRepository

class RegistrarDeudaUseCase(
    private val deudaRepository: DeudaRepository
) {

    suspend fun ejecutar(deuda: Deuda): Deuda {


        return deudaRepository.registrar(deuda)
    }
}
