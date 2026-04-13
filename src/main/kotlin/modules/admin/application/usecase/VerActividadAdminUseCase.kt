package com.finanzasana.modules.admin.application.usecase

import com.finanzasana.modules.admin.domain.model.ActividadAdmin
import com.finanzasana.modules.admin.domain.repository.AdminRepository

class VerActividadAdminUseCase(
    private val adminRepository: AdminRepository
) {

    suspend fun ejecutar(): List<ActividadAdmin> {
        return adminRepository.obtenerActividadReciente()
    }
}
