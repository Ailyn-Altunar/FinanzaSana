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
        edad: Int,
        idRol: Int
    ): Usuario {

        // 1. Validar que el email no exista
        val existente = usuarioRepository.verPorEmail(email)
        if (existente != null) {
            throw IllegalArgumentException("El email ya está registrado")
        }

        // 2. Hashear la contraseña
        val contrasenaHasheada = BCrypt.hashpw(contrasena, BCrypt.gensalt())

        // 3. Crear el modelo de dominio
        val usuario = Usuario(
            nombre = nombre,
            email = email,
            contrasena = contrasenaHasheada,
            edad = edad,
            idRol = idRol,
            nombreRol = obtenerNombreRol(idRol)
        )

        // 4. Guardar en la BD
        return usuarioRepository.guardar(usuario)
    }

    private fun obtenerNombreRol(idRol: Int): String =
        when (idRol) {
            1 -> "ADMIN"
            2 -> "CLIENTE"
            3 -> "SUPERADMIN"
            else -> "DESCONOCIDO"
        }
}