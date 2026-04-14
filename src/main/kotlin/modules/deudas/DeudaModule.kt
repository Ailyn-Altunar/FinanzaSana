package com.finanzasana.modules.deudas

import com.finanzasana.modules.deudas.application.usecase.*
import com.finanzasana.modules.deudas.domain.repository.AbonoRepository
import com.finanzasana.modules.deudas.domain.repository.DeudaRepository
import com.finanzasana.modules.deudas.infrastructure.persistence.PostgresAbonoRepository
import com.finanzasana.modules.deudas.infrastructure.persistence.PostgresDeudaRepository
import com.finanzasana.modules.categorias.domain.repository.CategoriaRepository
import com.finanzasana.modules.categorias.infrastructure.persistence.PostgresCategoriaRepository
import org.koin.dsl.module

val DeudaModule = module {

    factory { RegistrarDeudaUseCase(get()) }
    factory { ListarDeudasUsuarioUseCase(get()) }
    factory { VerDetalleDeudaUseCase(get()) }


    factory { RegistrarAbonoUseCase(get(), get()) }
    factory { ListarAbonosPorDeudaUseCase(get(), get()) }

    single<DeudaRepository> { PostgresDeudaRepository() }
    single<AbonoRepository> { PostgresAbonoRepository() }


    single<CategoriaRepository> { PostgresCategoriaRepository() }
}
