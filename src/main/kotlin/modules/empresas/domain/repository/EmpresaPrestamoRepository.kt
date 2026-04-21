package com.finanzasana.modules.empresas.domain.repository

import com.finanzasana.modules.empresas.domain.model.EmpresaPrestamo

interface EmpresaPrestamoRepository {

    suspend fun registrar(empresa: EmpresaPrestamo): EmpresaPrestamo

    suspend fun obtenerPorId(id: Int): EmpresaPrestamo?

    suspend fun listar(): List<EmpresaPrestamo>
}

