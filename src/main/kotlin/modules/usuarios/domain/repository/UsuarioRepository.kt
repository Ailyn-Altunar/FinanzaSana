package com.finanzasana.modules.usuarios.domain.repository

import com.finanzasana.modules.usuarios.domain.model.Usuario

interface UsuarioRepository {


    suspend fun verPorId(id: Int): Usuario?

    suspend fun verPorEmail(email: String): Usuario?

    suspend fun guardar(usuario: Usuario): Usuario

    suspend fun actualizar(id: Int, usuario: Usuario): Usuario?

    suspend fun eliminar(id: Int): Boolean

    suspend fun listar(): List<Usuario>


}