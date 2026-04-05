package com.finanzasana.modules.catalogoRol.application.usecase

import com.finanzasana.modules.catalogoRol.domain.repository.RolRepository

class ListarRolesUseCase(private val repo: RolRepository) {
    suspend operator fun invoke() = repo.listar()
}