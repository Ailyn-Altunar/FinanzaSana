package com.finanzasana.modules.catalogoRol.domain.repository

import com.finanzasana.modules.catalogoRol.domain.model.Rol

interface RolRepository {
    suspend fun listar(): List<Rol>
    suspend fun verPorId(idRol: Int): Rol?
}