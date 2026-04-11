package com.finanzasana.modules.deudas.domain.repository

import com.finanzasana.modules.deudas.domain.model.Deuda

interface DeudaRepository {

    suspend fun registrar(deuda: Deuda): Deuda

    suspend fun obtenerPorId(idDeuda: Int, idUsuario: Int): Deuda?

    suspend fun listarPorUsuario(idUsuario: Int): List<Deuda>

    suspend fun actualizarSaldo(idDeuda: Int, nuevoSaldo: Double)

    suspend fun liquidar(idDeuda: Int)
}
