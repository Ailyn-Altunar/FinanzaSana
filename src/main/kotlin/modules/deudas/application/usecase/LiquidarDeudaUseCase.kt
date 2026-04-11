package com.finanzasana.modules.deudas.application.usecase

import com.finanzasana.modules.deudas.domain.model.Abono
import com.finanzasana.modules.deudas.domain.repository.AbonoRepository
import com.finanzasana.modules.deudas.domain.repository.DeudaRepository
import java.time.LocalDateTime

class LiquidarDeudaUseCase(
    private val abonoRepository: AbonoRepository,
    private val deudaRepository: DeudaRepository
) {

    suspend fun ejecutar(idDeuda: Int, idUsuario: Int): Abono? {

        if (idDeuda <= 0) return null
        if (idUsuario <= 0) return null

        val deuda = deudaRepository.obtenerPorId(idDeuda, idUsuario)
            ?: return null

        if (deuda.saldoActual <= 0) return null

        val abono = Abono(
            id = null,
            idDeuda = idDeuda,
            idUsuario = idUsuario,
            monto = deuda.saldoActual,
            fecha = LocalDateTime.now()
        )

        val abonoGuardado = abonoRepository.registrar(abono)

        deudaRepository.liquidar(idDeuda)

        return abonoGuardado
    }
}
