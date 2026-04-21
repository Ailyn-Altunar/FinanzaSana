package com.finanzasana.modules.empresas.application.usecase

import com.finanzasana.modules.empresas.domain.model.EmpresaPrestamo
import com.finanzasana.modules.empresas.domain.repository.EmpresaPrestamoRepository

class ListarEmpresasUseCase(
    private val empresaRepository: EmpresaPrestamoRepository
) {

    suspend fun ejecutar(): List<EmpresaPrestamo> {
        return empresaRepository.listar()
    }
}
