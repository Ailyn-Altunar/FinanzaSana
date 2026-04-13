package com.finanzasana.modules.admin.application.usecase

import com.finanzasana.modules.admin.domain.model.AdminMetrics
import com.finanzasana.modules.admin.domain.repository.AdminRepository

class VerMetricasAdminUseCase(
    private val adminRepository: AdminRepository
) {

    suspend fun ejecutar(): AdminMetrics {
        return adminRepository.obtenerMetricas()
    }
}
