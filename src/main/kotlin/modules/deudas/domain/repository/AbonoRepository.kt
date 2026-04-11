package com.finanzasana.modules.deudas.domain.repository

import com.finanzasana.modules.deudas.domain.model.Abono

interface AbonoRepository {

    suspend fun registrar(abono: Abono): Abono

    suspend fun listarPorDeuda(idDeuda: Int, idUsuario: Int): List<Abono>
}
