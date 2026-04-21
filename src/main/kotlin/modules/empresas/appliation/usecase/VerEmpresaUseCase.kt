package com.finanzasana.modules.empresas.application.usecase

import com.finanzasana.modules.empresas.domain.model.EmpresaPrestamo
import com.finanzasana.modules.empresas.domain.repository.EmpresaPrestamoRepository

class VerEmpresaUseCase(
    private val empresaRepository: EmpresaPrestamoRepository
) {

    suspend fun ejecutar(id: Int): EmpresaPrestamo? {
        return empresaRepository.obtenerPorId(id)
    }
}
