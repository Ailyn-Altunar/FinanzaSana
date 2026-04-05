package com.finanzasana.modules.catalogoRol

import com.finanzasana.modules.catalogoRol.application.usecase.ListarRolesUseCase
import com.finanzasana.modules.catalogoRol.application.usecase.VerRolUseCase
import com.finanzasana.modules.catalogoRol.domain.repository.RolRepository
import com.finanzasana.modules.catalogoRol.infrastructure.persistence.PostgresRolRepository
import org.koin.dsl.module

val rolModule = module {

    // Casos de uso
    factory { ListarRolesUseCase(get()) }
    factory { VerRolUseCase(get()) }

    // Repositorio
    single<RolRepository> { PostgresRolRepository() }
}