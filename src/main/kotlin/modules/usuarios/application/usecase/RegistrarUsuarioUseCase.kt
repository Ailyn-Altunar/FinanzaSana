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
    ): Usuario? {

        if (nombre.isBlank()) return null
        if (email.isBlank()) return null
        if (contrasena.isBlank()) return null
        if (telefono.isBlank()) return null

        // Solo 2 roles válidos: 1 = admin, 2 = cliente
        if (idRol != 1 && idRol != 2) return null

        // Verificar si ya existe un usuario con ese email
        val existente = usuarioRepository.verPorEmail(email)
        if (existente != null) return null

        // Hashear contraseña
        val contrasenaHasheada = BCrypt.hashpw(contrasena, BCrypt.gensalt())

        val usuario = Usuario(
            nombre = nombre,
            email = email,
            contrasena = contrasenaHasheada,
            idRol = 2, // 🔥 SIEMPRE CLIENTE
            telefono = telefono
        )


        return usuarioRepository.guardar(usuario)
    }
}
