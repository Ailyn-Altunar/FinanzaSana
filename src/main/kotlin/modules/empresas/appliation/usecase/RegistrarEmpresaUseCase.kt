package com.finanzasana.modules.empresas.application.usecase

import com.finanzasana.modules.empresas.domain.model.EmpresaPrestamo
import com.finanzasana.modules.empresas.domain.repository.EmpresaPrestamoRepository

class RegistrarEmpresaUseCase(
    private val empresaRepository: EmpresaPrestamoRepository
) {

    suspend fun ejecutar(
        nombre: String,
        tasaInteres: Double
    ): EmpresaPrestamo {

        val empresa = EmpresaPrestamo(
            nombre = nombre,
            tasaInteres = tasaInteres
        )

        return empresaRepository.registrar(empresa)
    }
}
