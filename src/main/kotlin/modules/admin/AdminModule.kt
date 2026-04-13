package com.finanzasana.modules.admin

import com.finanzasana.modules.admin.application.usecase.VerActividadAdminUseCase
import com.finanzasana.modules.admin.application.usecase.VerMetricasAdminUseCase
import com.finanzasana.modules.admin.application.usecase.VerUsuariosAdminUseCase
import com.finanzasana.modules.admin.domain.repository.AdminRepository
import com.finanzasana.modules.admin.infrastructure.persistence.PostgresAdminRepository
import org.koin.dsl.module

val adminModule = module {

    // USE CASES
    factory { VerMetricasAdminUseCase(get()) }
    factory { VerActividadAdminUseCase(get()) }
    factory { VerUsuariosAdminUseCase(get()) }
    // REPOSITORY
    single<AdminRepository> { PostgresAdminRepository() }
}
