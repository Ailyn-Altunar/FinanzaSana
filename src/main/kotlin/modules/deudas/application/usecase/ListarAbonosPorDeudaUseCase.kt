package com.finanzasana.modules.deudas.application.usecase

import com.finanzasana.modules.deudas.domain.model.Abono
import com.finanzasana.modules.deudas.domain.repository.AbonoRepository
import com.finanzasana.modules.deudas.domain.repository.DeudaRepository

class ListarAbonosPorDeudaUseCase(
    private val abonoRepository: AbonoRepository,
    private val deudaRepository: DeudaRepository
) {

    suspend fun ejecutar(idDeuda: Int, idUsuario: Int): List<Abono> {

        if (idDeuda <= 0) return emptyList()
        if (idUsuario <= 0) return emptyList()

        val deuda = deudaRepository.obtenerPorId(idDeuda, idUsuario)
            ?: return emptyList()

        return abonoRepository.listarPorDeuda(idDeuda, idUsuario)
    }
}
