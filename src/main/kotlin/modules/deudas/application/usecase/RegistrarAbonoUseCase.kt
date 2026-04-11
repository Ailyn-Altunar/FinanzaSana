package com.finanzasana.modules.deudas.application.usecase

import com.finanzasana.modules.deudas.domain.model.Abono
import com.finanzasana.modules.deudas.domain.repository.AbonoRepository
import com.finanzasana.modules.deudas.domain.repository.DeudaRepository
import java.time.LocalDateTime

class RegistrarAbonoUseCase(
    private val abonoRepository: AbonoRepository,
    private val deudaRepository: DeudaRepository
) {

    suspend fun ejecutar(
        idDeuda: Int,
        idUsuario: Int,
        monto: Double
    ): Abono? {

        if (idDeuda <= 0) return null
        if (idUsuario <= 0) return null
        if (monto <= 0) return null

        val deuda = deudaRepository.obtenerPorId(idDeuda, idUsuario)
            ?: return null

        val nuevoSaldo = deuda.saldoActual - monto
        if (nuevoSaldo < 0) return null

        val abono = Abono(
            id = null,
            idDeuda = idDeuda,
            idUsuario = idUsuario,
            monto = monto,
            fecha = LocalDateTime.now()
        )

        val abonoGuardado = abonoRepository.registrar(abono)

        deudaRepository.actualizarSaldo(idDeuda, nuevoSaldo)

        return abonoGuardado
    }
}
