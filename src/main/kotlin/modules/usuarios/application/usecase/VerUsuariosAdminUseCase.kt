package com.finanzasana.modules.usuarios.application.usecase

import com.finanzasana.modules.usuarios.domain.model.Usuario
import com.finanzasana.modules.usuarios.domain.repository.UsuarioRepository

class VerUsuariosAdminUseCase(
    private val usuarioRepository: UsuarioRepository
) {

    suspend fun ejecutar(idUsuarioSolicitante: Int): List<Usuario> {

        val solicitante = usuarioRepository.verPorId(idUsuarioSolicitante)
            ?: throw IllegalArgumentException("El usuario solicitante no existe")

        if (solicitante.idRol != 1) {
            throw IllegalAccessException("No tienes permisos para ver la lista de usuarios")
        }

        return usuarioRepository.listar()
    }
}