package com.finanzasana.modules.empresas

import com.finanzasana.modules.empresas.application.usecase.ListarEmpresasUseCase
import com.finanzasana.modules.empresas.application.usecase.RegistrarEmpresaUseCase
import com.finanzasana.modules.empresas.application.usecase.VerEmpresaUseCase
import com.finanzasana.modules.empresas.domain.repository.EmpresaPrestamoRepository
import com.finanzasana.modules.empresas.infrastructure.persistence.PostgresEmpresaPrestamoRepository
import org.koin.dsl.module

val empresaPrestamoModule = module {

    factory { RegistrarEmpresaUseCase(get()) }
    factory { ListarEmpresasUseCase(get()) }
    factory { VerEmpresaUseCase(get()) }

    single<EmpresaPrestamoRepository> { PostgresEmpresaPrestamoRepository() }
}
