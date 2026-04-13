package com.finanzasana.modules.admin.application.usecase


import com.finanzasana.modules.admin.domain.model.UserAdmin
import com.finanzasana.modules.admin.domain.repository.AdminRepository

class VerUsuariosAdminUseCase(
    private val adminRepository: AdminRepository
) {

    suspend operator fun invoke(): List<UserAdmin> {
        return adminRepository.obtenerUsuariosAdmin()
    }
}
