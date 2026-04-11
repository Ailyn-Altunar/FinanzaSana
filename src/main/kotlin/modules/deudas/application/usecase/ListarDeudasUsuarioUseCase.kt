package com.finanzasana.modules.deudas.application.usecase

import com.finanzasana.modules.deudas.domain.model.Deuda
import com.finanzasana.modules.deudas.domain.repository.DeudaRepository

class ListarDeudasUsuarioUseCase(
    private val deudaRepository: DeudaRepository
) {

    suspend fun ejecutar(idUsuario: Int): List<Deuda> {

        if (idUsuario <= 0) return emptyList()

        return deudaRepository.listarPorUsuario(idUsuario)
    }
}
