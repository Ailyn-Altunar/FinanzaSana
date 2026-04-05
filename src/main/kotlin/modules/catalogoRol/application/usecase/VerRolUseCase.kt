package com.finanzasana.modules.catalogoRol.application.usecase

import com.finanzasana.modules.catalogoRol.domain.repository.RolRepository

class VerRolUseCase(private val repo: RolRepository) {
    suspend operator fun invoke(id: Int) = repo.verPorId(id)
}