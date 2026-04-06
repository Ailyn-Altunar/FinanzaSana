package com.finanzasana.modules.usuarios.application.usecase

import com.finanzasana.modules.usuarios.domain.model.Usuario
import com.finanzasana.modules.usuarios.domain.repository.UsuarioRepository
import org.mindrot.jbcrypt.BCrypt

class RegistrarUsuarioUseCase(
    private val usuarioRepository: UsuarioRepository
) {

    suspend fun ejecutar(
        nombre: String,
        email: String,
        contraseña: String,
        idRol: Int
    ): Usuario {

        val existente = usuarioRepository.verPorEmail(email)
        if (existente != null) {
            throw IllegalArgumentException("El email ya está registrado")
        }

        val contraseñaHasheada = BCrypt.hashpw(contraseña, BCrypt.gensalt())

        val usuario = Usuario(
            nombre = nombre,
            email = email,
            contraseña = contraseñaHasheada,
            idRol = idRol,
            nombreRol = obtenerNombreRol(idRol)
        )

        return usuarioRepository.guardar(usuario)
    }

    private fun obtenerNombreRol(idRol: Int): String =
        when (idRol) {
            1 -> "ADMIN"
            2 -> "CLIENTE"
            else -> "DESCONOCIDO"
        }
}