package com.finanzasana.modules.catalogoRol


import com.finanzasana.modules.catalogoRol.domain.repository.RolRepository
import com.finanzasana.modules.catalogoRol.infrastructure.persistence.PostgresRolRepository
import org.koin.dsl.module

val rolModule = module {

    single<RolRepository> { PostgresRolRepository() }
}