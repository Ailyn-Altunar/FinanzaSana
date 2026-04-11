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
        contrasena: String,
        idRol: Int,
        telefono: String
    ): Usuario {

        val existente = usuarioRepository.verPorEmail(email)
        if (existente != null) {
            throw IllegalArgumentException("El email ya está registrado")
        }

        val contrasenaHasheada = BCrypt.hashpw(contrasena, BCrypt.gensalt())

        val usuario = Usuario(
            nombre = nombre,
            email = email,
            contrasena = contrasenaHasheada,
            idRol = idRol,
            nombreRol = obtenerNombreRol(idRol),
            telefono = telefono
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
